(comment
  "HAML like templating library"
)


;; @author Antonio Garrote Hernandez
;;
;; This is free software available under the terms of the
;; GNU Lesser General Public License
;;

(ns cljhaml)

(defn keyword-to-string
  "Transforms a keyword into a string minus ':'"
  ([kw] (. (str kw) (substring 1 (. (str kw) length)))))

(defmacro h=
  "Main Haml function"
  ([& params]
     (let [elems (if (keyword? (first params))
                   (let [kws (keyword-to-string (first params))]
                     (if (and (= -1 (. kws (indexOf "#")))
                              (= -1 (. kws (indexOf "."))))
                       {:tag kws :class nil :id nil}
                       (if (and (= -1 (. kws (indexOf "#")))
                                (not (= -1 (. kws (indexOf ".")))))
                         (let [parts (. kws (split "\\."))
                               tag (if (= (first parts) "") "div" (first parts))
                               class (loop [classes (rest parts)
                                            class_txt ""]
                                       (if (nil? classes)
                                         class_txt
                                         (recur (rest classes)
                                                (str class_txt " " (first classes)))))]
                           {:tag tag :class class :id nil})
                         (if (and (not (= -1 (. kws (indexOf "#"))))
                                  (= -1 (. kws (indexOf "."))))
                           (let [parts (. kws (split "#"))
                                 tag (if (= (first parts) "") "div" (first parts))
                                 id (second parts)]
                             {:tag tag :class nil :id id})
                           (let [idi (. kws (indexOf "#"))
                                 idc (. kws (indexOf "."))]
                             (if (> idi idc)
                               (let [partsc (. kws (split "\\."))
                                     tag (first partsc)]
                                 (loop [other-classes (rest partsc)
                                        the-classes ""
                                        the-id ""]
                                   (if (nil? other-classes)
                                     {:tag tag :class the-classes :id the-id}
                                     (let [this-class (first other-classes)
                                           this-idi (. this-class (indexOf "#"))]
                                       (if (= -1 this-idi)
                                         (recur (rest other-classes)
                                                (str the-classes " " this-class)
                                                the-id)
                                         (let [this-class-parts (. this-class (split "#"))]
                                           (recur (rest other-classes)
                                                  (str the-classes " " (first this-class-parts))
                                                  (second this-class-parts))))))))
                               (let [partsc (. kws (split "#"))
                                     tag (first partsc)
                                     restc (. (second partsc) (split "\\."))
                                     id (first restc)
                                     class (loop [classes (rest restc)
                                                  class_txt ""]
                                             (if (nil? classes)
                                               class_txt
                                               (recur (rest classes)
                                                      (str class_txt " " (first classes)))))]
                                 {:tag tag :class class :id id}))))))))
           tag (:tag elems)
           id (:id elems)
           class (:class elems)
           attrs (loop [parts params]
                   (if (nil? parts)
                     nil
                     (let [this-part (first parts)]
                       (if (map? this-part)
                         this-part
                         (recur (rest parts))))))
           self-closed? (let [last-param (last params)]
                          (if (symbol? last-param)
                            (if (= (str last-param) "/") true false)
                            false))
           content (loop [looking params
                          acum ""]
                     (if (nil? looking)
                       acum
                       (let [maybe-content (first looking)]
                         (if (or (keyword? maybe-content)
                                 (and (symbol? maybe-content)
                                      (= \. (first (str maybe-content))))
                                 (and (symbol? maybe-content)
                                      (= \# (first (str maybe-content))))
                                 (map? maybe-content))
                           (recur (rest looking)
                                  acum)
                           (recur (rest looking)
                                  (str acum (eval maybe-content)))))))
           pre (str "<" tag
                    (when (not (nil? id))
                      (str " id='" id "'"))
                    (when (not (nil? class))
                      (str " class='" (. class (substring 1)) "'"))
                    (loop [attr-keys (keys attrs)
                           acum ""]
                      (if (not (nil? attr-keys))
                        (let [this-key (first attr-keys)
                              this-val (get attrs this-key)]
                          (recur (rest attr-keys)
                                 (str acum " " (keyword-to-string this-key) "='" (eval this-val) "'")))
                        acum)))]
       (if self-closed?
         (str pre "/>")
         (str pre ">" content "</" tag ">")))))


(defn h-file
  "Loads and parses a Haml template"
  ([path]
     (load-file path)))


(comment "Some tests")

(use 'clojure.contrib.test-is)

(deftest test-1
  (is (= (h= :#content
             (h= :.title
                 (h= :h1 "Teen Wolf")
                 (h= :a {:href "/"} "Home")))
         "<div id='content'><div class='title'><h1>Teen Wolf</h1><a href='/'>Home</a></div></div>")))

(deftest test-2
  (is (= (h= :one
             (h= :two
                 (h= :three "Hey there")))
         "<one><two><three>Hey there</three></two></one>")))

(deftest test-3
  (is (= (h= :head {:name "doc_head"}
             (h= :script {:type (str "text/" "javascript")
                          :src (str "/docs/rdoc/javascripts/script_" (+ 2 7))}))
      "<head name='doc_head'><script src='/docs/rdoc/javascripts/script_9' type='text/javascript'></script></head>")))

(deftest test-4
  (is (= (h= :sandwich {:bread "whole wheat"} /)
         "<sandwich bread='whole wheat'/>")))

(deftest test-5
  (is (= (h= :div#things
             (h= :span#rice "Chicken Fried")
             (h= :p.beans {:food "true"} "The magical fruit")
             (h= :h1.class.otherclass#id "La La La"))
         "<div id='things'><span id='rice'>Chicken Fried</span><p class='beans' food='true'>The magical fruit</p><h1 id='id' class='class otherclass'>La La La</h1></div>")))
(ns test-clj-haml
  (:use [clojure.test]
        [clj-haml]))

(comment "Some tests")

(comment (use 'clojure.test))

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
  (is (let [return-str (h= :head {:name "doc_head"}
                           (h= :script {:type (str "text/" "javascript")
                                        :src (str "/docs/rdoc/javascripts/script_" (+ 2 7))}))]
        (or (= return-str
               "<head name='doc_head'><script src='/docs/rdoc/javascripts/script_9' type='text/javascript'></script></head>")
            (= return-str
               "<head name='doc_head'><script type='text/javascript' src='/docs/rdoc/javascripts/script_9'></script></head>")))))

(deftest test-4
  (is (= (h= :sandwich {:bread "whole wheat"} /)
         "<sandwich bread='whole wheat'/>")))

(deftest test-5
  (is (= (h= :div#things
             (h= :span#rice "Chicken Fried")
             (h= :p.beans {:food "true"} "The magical fruit")
             (h= :h1.class.otherclass#id "La La La"))
         "<div id='things'><span id='rice'>Chicken Fried</span><p class='beans' food='true'>The magical fruit</p><h1 id='id' class='class otherclass'>La La La</h1></div>")))

(deftest test-!!!-1
  (is (= (!!!
          (h= :p "hola")
          (h= :p "adios"))
         "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//ENhttp://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><p>hola</p><p>adios</p>")))

(deftest test-!!!-2
  (is (= (!!! :xml
          (h= :p "hola")
          (h= :p "adios"))
         "<?xml version='1.0' encoding='utf-8' ?><p>hola</p><p>adios</p>")))

(deftest test-!!!-3
  (is (= (!!!)
         "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//ENhttp://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")))

(deftest test-!!!-4
  (is (= (!!! :xml)
         "<?xml version='1.0' encoding='utf-8' ?>")))

(deftest test-!!!-5
  (is (= (!!! :xml "iso-8859-1")
         "<?xml version='1.0' encoding='iso-8859-1' ?>")))

(deftest test-!!!-6
  (is (= (!!! 1.1)
         "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1 Transitional//ENhttp://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")))

(deftest test-!!!-7
  (is (= (!!! Strict)
         "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//ENhttp://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")))

(deftest test-!!!-8
  (is (= (!!! Strict (h= :p "hola"))
         "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//ENhttp://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><p>hola</p>")))

clj-haml is small HTML template library for Clojure inspired by the Ruby template library Haml (http://haml.hamptoncatlin.com/).

== Installation

- clj-haml now uses Leiningen as a bulding tool and Clojars to manage the jar dependencies.
  Take a look at http://clojars.org/ and http://github.com/technomancy/leiningen for more
  information.
- clj-haml can be used with maven from the following repository clojars repository, take a look
  at the included POM file.

<dependency>
  <groupId>clj-haml</groupId>
  <artifactId>clj-haml</artifactId>
  <version>0.2.1-SNAPSHOT</version>
</dependency>

- The Leiningen dependecy can be included in you leiningen project with the following dep:

[clj-haml "0.2.1-SNAPSHOT"]

- if you have installed Leiningen you can build and test clj-haml with the following commands:

$lein compile
$lein test

== Use

A Ruby Haml example could be:

%div#things
    %span#rice Chicken Fried
    %p.beans{ :food => 'true' } The magical fruit
    %h1.class.otherclass#id La La La


The equivalent clj-haml code would be:

(h= :div#things
        (h= :span#rice "Chicken Fried")
        (h= :p.beans {:food "true"} "The magical fruit")
        (h= :h1.class.otherclass#id "La La La"))



There most relevant function is h=:

(h= :[selector] [attributes] [content] [/])

where:

- selector: an optional keyword with a HTML tag, identifier and classes specified with CSS syntax.
            If no selector is given or if no tag name is at the begining of the symbol, 'div' is assumed.
            Some examples: :p#myid.myclass1.myclass2, :p.myclass1, :p#myid, :#myid, :.myclass1
- attributes: a optional map with keys and values for attributes names and values, e.g: {:href "/test.html"}
- content: a form or forms that will be evaluated and whose resulting value will be inserted in the tag.
- /: a backslash that, if present, will generate a self-closed tag, e.g: (h= :br /) will be parsed to <br/>


Other functions are

- (h-file path), that will load the file at the path parameter.
- (!!! [:options] [forms]) will insert a document prolog, and then process the rest of optionals forms. Some options are available:
    - (!!!) -> "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//ENhttp://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
    - (!!! :xml) -> "<?xml version='1.0' encoding='utf-8' ?>"
    - (!!! 1.1) -> "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1 Transitional//ENhttp://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
    - (!!! Strict) -> "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//ENhttp://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
    - (!!! :xml "iso-8859-15") -> "<?xml version='1.0' encoding='iso-8859-1' ?>"
-(with-haml-template path :format [bindings-list]), Looks for a templat file ending with path.format.haml,
    and parses the template using the optional bindings list for the vars in the template. For instance:
    (with-haml-template "/my/path/to/template" :html [vara 12 varb "foo"]) will parse the file
    /my/path/to/template.html.haml with bindings vara = 12 and varb = "foo".


== Tests

Some tests are included in the source file. You can run them with the command 'mvn test'

== Changelog

- version 0.1.0  (02/28/2009): First version.
- version 0.2.0  (10/05/2009): Updated to last version of clojure. Clojure POM ready.
- version 0.2.1  (10/05/2009): Updated to Leiningen and clojars infrastrcture.
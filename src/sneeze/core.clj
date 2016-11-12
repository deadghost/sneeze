(ns sneeze.core
  (:require [clojure.data.xml :as xml]
            [net.cgrand.enlive-html :as enlive]))

;; TODO:
;; Check with ridcully and tdammers on #clojure for input
;; Write tests

(defn emit-sexp
  "Emits the Element to a Hiccup-Style Structure.
  Reverse of xml/sexp-as-element.
  See: https://github.com/clojure/data.xml/pull/10
       http://dev.clojure.org/jira/browse/DXML-22"
  [e]
  (cond (or (= (type e) clojure.data.xml.Element)
            (and (map? e) (contains? e :tag)))
        (vec (concat [(:tag e)]
                     (if (empty? (:attrs e)) [] [(:attrs e)])
                     (map emit-sexp (:content e))))
        :else e))

;; For debugging purposes.
(def print-html
  "Prints HTML when given a defhtml function output.
  Example: (print-html (include-css \"/css/file.css\"))
           => <link href=\"/css/file.css\" rel=\"stylesheet\" />"
  (comp print (partial apply str) enlive/emit*))

;; Private function that needs to be aliased.
(def static-selector? #'enlive/static-selector?)

(defn at [node-or-nodes & rule]
  (let [[s t] rule]
    (-> node-or-nodes enlive/as-nodes
        (enlive/transform
         (if (static-selector? s)
           (enlive/cacheable s)
           s)
         t)
        first)))

(defmacro enlive-transformer->sneeze-transformer [transformer]
  `(defn ~transformer [src# selector# content#]
     (->> (at (xml/sexp-as-element src#)
              selector# (~(symbol (str "enlive/" transformer))
                         (enlive/html content#)))
          (emit-sexp))))

;; Example output:
;; (defn append [src selector content]
;;   (->> (at (xml/sexp-as-element src)
;;            selector (enlive/append (enlive/html content)))
;;        (emit-sexp)))

;; (def base-html
;;   [:html
;;    [:head]
;;    [:body
;;     [:div {:id "id-name"}]]])

(enlive-transformer->sneeze-transformer content)
;; (content base-html [:body] [:p "hi"])
(enlive-transformer->sneeze-transformer append)
;; (append base-html [:body] [:p "hi"])
(enlive-transformer->sneeze-transformer prepend)
;; (prepend base-html [:body] [:p "hi"])
(enlive-transformer->sneeze-transformer after)
;; (after base-html [:#id-name] [:p "hi"])
(enlive-transformer->sneeze-transformer before)
;; (before base-html [:#id-name] [:p "hi"])
(enlive-transformer->sneeze-transformer substitute)
;; (substitute base-html [:#id-name] [:p "hi"])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; The transformers that didn't fit the above macro mold.
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn html-content [src selector content]
  (->> (at (xml/sexp-as-element src)
           selector (enlive/html-content content))
       (emit-sexp)))
;; (html-content base-html [:#id-name] "<p>hi</p>")

(defn wrap [src selector & content]
  (->> (at (xml/sexp-as-element src)
           selector (apply enlive/wrap content))
       (emit-sexp)))
;; (wrap base-html [:#id-name] :div {:id "wrapper"})

;; TODO: unwrap
;; The enlive implementation is kind of crappy. It is literally just an alias
;; for :content. I'm also not exactly sure what the behavior is supposed to be.

(defn set-attr [src selector & attrs]
  (->> (at (xml/sexp-as-element src)
           selector (apply enlive/set-attr attrs))
       (emit-sexp)))
;; (set-attr base-html [:#id-name] :class "foo")

(defn remove-attr [src selector & attrs]
  (->> (at (xml/sexp-as-element src)
           selector (apply enlive/remove-attr attrs))
       (emit-sexp)))
;; (remove-attr base-html [:#id-name] :id)

(defn add-class [src selector & classes]
  (->> (at (xml/sexp-as-element src)
           selector (apply enlive/add-class classes))
       (emit-sexp)))
;; (add-class base-html [:#id-name] "class1" "class2")

(defn remove-class [src selector & classes]
  (->> (at (xml/sexp-as-element src)
           selector (apply enlive/remove-class classes))
       (emit-sexp)))

;; To be implemented:
;; move

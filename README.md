# sneeze

Clojure HTML templating library. Hiccup + Selectors = Wow.

## Why sneeze?

1. Hiccup syntax is good. It is easy to read and easy to compose.
2. Emerging frameworks like reagent require hiccup syntax.
3. Modifying HTML via selectors can very flexible and easy.

For a comparison of HTML libraries, see: [clj-html-comparison](https://github.com/deadghost/clj-html-comparison).

## Installation

Add the following dependency to your `project.clj`:

    [sneeze "0.2.0"]

## Usage

```clojure
(ns myproject.namespace
  (:require [sneeze.core :as sneeze]))

(def base-html
  [:html
   [:head]
   [:body
    [:div {:id "id-name" 
	       :class "class-one class-two"}]]])

(def index-page
  "Sneeze makes it easy to thread HTML transformations for easy composability."
  (-> base-html
      (sneeze/append [:head] [:title "My Appended Site's Title"])
      (sneeze/after [:#id-name]
                    [:footer
                      [:p "My footer info here."]])))

;; [:html 
;;   [:head 
;;     [:title "My Appended Site's Title"]] 
;;   [:body 
;;     [:div {:id "id-name" :class "class-one class-two"}] 
;; 	   [:footer [:p "My footer info here."]]]]

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; More Examples
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Replaces content of [:body] with [:p "hi"]
(content base-html [:body] [:p "hi"])
;; Appends [:p "hi"] to [:body]
(append base-html [:body] [:p "hi"])
;; Prepends [:p "hi"] to [:body]
(prepend base-html [:body] [:p "hi"])
;; Adds [:p "hi"] after [:#id-name]
(after base-html [:#id-name] [:p "hi"])
;; Adds [:p "hi"] before [:#id-name]
(before base-html [:#id-name] [:p "hi"])
;; Replaces [:#id-name] with [:p "hi"] 
(substitute base-html [:#id-name] [:p "hi"])
;; Replaces content of [:body] with "<p>hi</p>"
(html-content base-html [:#id-name] "<p>hi</p>")
;; Wraps [:div {:id "wrapper"}] around [#:id-name]
(wrap base-html [:#id-name] :div {:id "wrapper"})
;; Sets class attribute of [:#id-name] to "foo"
(set-attr base-html [:#id-name] :class "foo")
;; Removes id attribute of [:#id-name]
(remove-attr base-html [:#id-name] :id)
;; Adds the classes "class-three" and "class-four" to [:#id-name]
(add-class base-html [:#id-name] "class-three" "class-four")
;; Removes the classes "class-one" and "class-two" from [#id-name]
(remove-class base-html [:#id-name] "class-one" "class-two")
```

### Emitting HTML with Hiccup

Include hiccup in your `project.clj`.
	
```clojure
(use 'hiccup.core) ; For simplicity only, it's better to put hiccup in :require.

(html index-page) ; index-page defined previously in example code.
;; => <html><head><title>My Appended Site's Title</title></head><body><div class=\"class-one class-two\" id=\"id-name\"></div><footer><p>My footer info here.</p></footer></body></html>
```

## License

Copyright © 2016 Kenny Liu

Distributed under the Eclipse Public License, the same as Clojure. 

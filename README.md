# sneeze

Clojure HTML templating library. Hiccup + Selectors = Wow.

## Why sneeze?

1. Hiccup syntax is good. It is easy to read and easy to compose.
2. Emerging frameworks like reagent require hiccup syntax.
3. Modifying HTML via selectors can very flexible and easy.

## Library Comparison
### Hiccup
Sneeze uses hiccup. When you want something more powerful you can sneeze.
### Enlive
Sneeze has enlive for inspiration. Enlive is great for reading .html files into
clojure and running transformations on them using selectors. It falls short in
hiccup compatibility and composability. Enlive is very macro heavy and this can
cause issues when your usage isn't quite in the target use case.
### Enliven
Enlive v2. It's not quite baked and edible. 
### Selmer
Never used it, so can't really say.

## Usage

    (ns myproject.namespace
      (:require [sneeze.core :as sneeze]))

    (def base-html
      [:html
       [:head]
       [:body
        [:div {:id "id-name"}]]])
    
    (def index-page
      (-> base-html
          (sneeze/append [:head] [:title "My Appended Site's Title"])
          (sneeze/after [:#id-name]
                        [:footer
                          [:p "My footer info here."]])))

    ;; [:html 
	;;   [:head 
	;;     [:title "My Appended Site's Title"]] 
	;;   [:body 
	;;     [:div {:id "id-name"}] 
	;; 	   [:footer [:p "My footer info here."]]]]

	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
    ;; More Examples
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
    ;; (content base-html [:body] [:p "hi"])
    ;; (append base-html [:body] [:p "hi"])
    ;; (prepend base-html [:body] [:p "hi"])
    ;; (after base-html [:#id-name] [:p "hi"])
    ;; (before base-html [:#id-name] [:p "hi"])
    ;; (substitute base-html [:#id-name] [:p "hi"])
    ;; (html-content base-html [:#id-name] "<p>hi</p>")
    ;; (wrap base-html [:#id-name] :div {:id "wrapper"})
    ;; (set-attr base-html [:#id-name] :class "foo")
    ;; (remove-attr base-html [:#id-name] :id)
    ;; (add-class base-html [:#id-name] "class1" "class2")
    ;; (remove-class base-html [:#id-name] "class1" "class2")
	
## License

Copyright © 2016 Kenny Liu

Distributed under the Eclipse Public License, the same as Clojure. 

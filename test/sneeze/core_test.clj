(ns sneeze.core-test
  (:require [clojure.test :refer :all]
            [sneeze.core :refer :all]))

(def base-html
  [:html
   [:head]
   [:body
    [:div {:id "id-name"}]]])

(deftest transformers
  (testing "Transformer functions basic functionality.")
  (is (= (content base-html [:body] [:p "hi"])
         [:html [:head] [:body [:p "hi"]]]))
  (is (= (append base-html [:body] [:p "hi"])
         [:html [:head] [:body [:div {:id "id-name"}] [:p "hi"]]]))
  (is (= (prepend base-html [:body] [:p "hi"])
         [:html [:head] [:body [:p "hi"] [:div {:id "id-name"}]]]))
  (is (= (after base-html [:body] [:p "hi"])
         [:html [:head] [:body [:div {:id "id-name"}]] [:p "hi"]]))
  (is (= (before base-html [:body] [:p "hi"])
         [:html [:head] [:p "hi"] [:body [:div {:id "id-name"}]]]))
  (is (= (substitute base-html [:body] [:p "hi"])
         [:html [:head] [:p "hi"]]))
  (is (= (html-content base-html [:#id-name] "<p>hi</p>")
         [:html [:head] [:body [:div {:id "id-name"} [:p "hi"]]]]))
  (is (= (wrap base-html [:#id-name] :div {:id "wrapper"})
         [:html [:head] [:body [:div {:id "wrapper"} [:div {:id "id-name"}]]]]))
  (is (= (set-attr base-html [:#id-name] :class "foo")
         [:html [:head] [:body [:div {:id "id-name", :class "foo"}]]]))
  (is (= (remove-attr base-html [:#id-name] :id)
         [:html [:head] [:body [:div]]]))
  (is (= (add-class base-html [:#id-name] "class1" "class2")
         [:html [:head] [:body [:div {:id "id-name", :class "class1 class2"}]]]))
  ;; [TODO] remove-class test here
  )

(deftest content-rest-arg
  (testing "& content parameter")
  (is (= (content base-html [:body] [:p "hi"] [:p "bye"])
         [:html [:head] [:body [:p "hi"] [:p "bye"]]]))
  (is (= (append base-html [:body] [:p "hi"] [:p "bye"])
         [:html [:head] [:body [:div {:id "id-name"}] [:p "hi"] [:p "bye"]]]))
  (is (= (prepend base-html [:body] [:p "hi"] [:p "bye"])
         [:html [:head] [:body [:p "hi"] [:p "bye"] [:div {:id "id-name"}]]]))
  (is (= (after base-html [:body] [:p "hi"] [:p "bye"])
         [:html [:head] [:body [:div {:id "id-name"}]] [:p "hi"] [:p "bye"]]))
  (is (= (before base-html [:body] [:p "hi"] [:p "bye"])
         [:html [:head] [:p "hi"] [:p "bye"] [:body [:div {:id "id-name"}]]]))
  (is (= (substitute base-html [:body] [:p "hi"] [:p "bye"])
         [:html [:head] [:p "hi"] [:p "bye"]])))

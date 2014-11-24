(ns reagent-modals.modals
  (:require [reagent.core :as r :refer [atom]]
            [goog.dom :as dom]
            [goog.events :as events])
  (:import [goog.events EventType]))

;;; Make sure to create the modal-window element somewhere in the dom.
;;; Recommended: at the start of the document.


(def modal-id "reagent-modal")

(def modal-content (atom {:content [:div] 
                          :shown nil 
                          :size nil}))

(defn get-modal []
  (dom/getElement modal-id))


(defn show-modal! [keyboard]
  (let [m (js/jQuery (get-modal))]
    (.call (aget m "modal") m #js {:keyboard keyboard})
    (.call (aget m "modal") m "show")
    m))

(defn close-button
  "A pre-configured close button. Just include it anywhere in the
   modal to let the user dismiss it." []
   [:button.close {:type "button" :data-dismiss "modal"}
     [:span.glyphicon.glyphicon-remove {:aria-hidden "true"}]
     [:span.sr-only "Close"]])



(defn modal-window* []
  (let [{:keys [content size]} @modal-content
        size-class {:lg "modal-lg"
                    :sm "modal-sm"}]
    [:div.modal.fade {:id modal-id :tab-index -1 :role "dialog"}
     [:div.modal-dialog {:class (get size-class size)}
      [:div.modal-content
       content]]]))

(def modal-window
  (with-meta
    modal-window*
    {:component-did-mount (fn [e] (let [m (js/jQuery (get-modal))]
                                    (.call (aget m "on") m "hidden.bs.modal"
                                           #(do (reset! modal-content [:div]))) ;;clear the modal when hidden
                                    (when-let [shown-fn (:shown @modal-content)]
                                      (.call (aget m "on") m "shown.bs.modal" shown-fn)) ;; shown callback
                                    (when-let [hide-fn (:hide @modal-content)]
                                      (.call (aget m "on") m "hide.bs.modal" hide-fn))
                                    (when-let [hidden-fn (:hidden @modal-content)]
                                      (.call (aget m "on") m "hidden.bs.modal" hidden-fn))))}))


;;; main function


(defn modal!
  "Update and show the modal window. `reagent-content' is a normal
   reagent component. `configs' is an optional map of advanced
   configurations: 

   - :shown -> a function called once the modal is shown.
   - :hide -> a function called once the modal is asked to hide.
   - :hidden -> a function called once the modal is hidden.
   - :size -> Can be :lg (large) or :sm (small). Everything else defaults to medium.
   - :keyboard -> if `esc' can dismiss the modal. Default to true."
  ([reagent-content] (modal! reagent-content nil))
  ([reagent-content configs]
     (reset! modal-content (merge {:content reagent-content} configs))
     (show-modal! (get configs :keyboard true))))

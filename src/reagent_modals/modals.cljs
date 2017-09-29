(ns reagent-modals.modals
  (:require [reagent.core :as r :refer [atom]]
            [goog.dom :as dom]
            [goog.events :as events])
  (:import [goog.events EventType]))

;;; Make sure to create the modal-window element somewhere in the dom.
;;; Recommended: at the start of the document.


(def modal-id "reagent-modal")

(defonce modal-content (atom {:content nil;[:div]
                              :shown nil
                              :size nil}))

(defn get-modal []
  (dom/getElement modal-id))

(defn- with-opts [opts]
  (let [m (js/jQuery (get-modal))]
    (.call (aget m "modal") m opts)
    (.call (aget m "modal") m "show")
    m))

(defmulti show-modal! (fn [args] (map? args))) ;; backward compatibility
(defmethod show-modal! true
  [{:keys [keyboard backdrop] :or {keyboard true backdrop true}}]
  (with-opts #js {:keyboard keyboard :backdrop  backdrop}))

(defmethod show-modal! false [keyboard]
  (with-opts #js {:keyboard keyboard}))

(defn close-modal! []
  (let [m (js/jQuery (get-modal))]
    (.call (aget m "modal") m "hide")))

(defn close-button
  "A pre-configured close button. Just include it anywhere in the
   modal to let the user dismiss it." []
   [:button.close {:type "button" :data-dismiss "modal"}
     [:span.glyphicon.glyphicon-remove {:aria-hidden "true"}]
     [:span.sr-only "Close"]])



(defn modal-window []
  (let [unmounting? (atom nil)]
    (r/create-class
     {:component-did-mount
      (fn [e] (let [m (js/jQuery (get-modal))]
                (.call (aget m "on") m "hidden.bs.modal"
                       #(do (when-let [f (:hidden @modal-content)] (f))
                            ;; don't erase the content if we are
                            ;; unmounting the modal window, we are
                            ;; probably only reloading the app.
                            (when-not @unmounting?
                              (swap! modal-content assoc :content nil))))
                (.call (aget m "on") m "shown.bs.modal"
                       #(when-let [f (:shown @modal-content)] (f)))
                (.call (aget m "on") m "hide.bs.modal"
                       #(when-let [f (:hide @modal-content)] (f))))
        ;; we might need to show the modal after an app reload.
        (let [mc @modal-content]
          (when (:content mc)
            (show-modal! mc))))
      :component-will-unmount (fn []
                                (reset! unmounting? true)
                                (close-modal!))
      :reagent-render
      (fn []
        (let [{:keys [content size]} @modal-content
              size-class {:lg "modal-lg"
                          :sm "modal-sm"}]
          [:div.modal.fade {:id modal-id :tab-index -1 :role "dialog"}
           [:div.modal-dialog {:class (get size-class size)}
            [:div.modal-content
             content]]]))})))


;;; main function


(defn modal!
  "Update and show the modal window. `reagent-content' is a normal
   reagent component. `configs' is an optional map of advanced
   configurations:

   - :shown -> a function called once the modal is shown.
   - :hide -> a function called once the modal is asked to hide.
   - :hidden -> a function called once the modal is hidden.
   - :size -> Can be :lg (large) or :sm (small). Everything else defaults to medium.
   - :keyboard -> if true, `esc' key can dismiss the modal. Default to true.
   - :backdrop -> true (default): backdrop. 
                  \"static\" : backdrop, but doesn't close the model when clicked upon. 
                  false : no backdrop."
  ([reagent-content] (modal! reagent-content nil))
  ([reagent-content configs]
   (reset! modal-content (merge {:content reagent-content} configs))
   (show-modal! (select-keys configs [:keyboard :backdrop]))))

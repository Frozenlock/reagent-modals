# reagent-modals

Bootstrap modal components for Reagent.

Takes care of all the little details for creating a modal with Bootstrap.

<img src="https://raw.githubusercontent.com/Frozenlock/reagent-modals/master/modal-example.png"
 alt="Modal demo" title="Modal demo"/>

## Install
Add this to your project dependencies:

[![Clojars Project](http://clojars.org/org.clojars.frozenlock/reagent-modals/latest-version.svg)](http://clojars.org/org.clojars.frozenlock/reagent-modals)


## Usage

Make sure you have the jQuery and Boostrap javascript loaded in your page:
```html
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
```


Include the `modal-window` component in the root of your document:

```clj
(defn main-page []
	[:div
	  [:h1 "Page title"]
	  [:span "Some content"]
	  [reagent-modals/modal-window]]) ;  <-------
```

Now, everytime you want to show a modal to the user, you just need to call `modal!`:

```clj
:on-click #(reagent-modals/modal! [:div "some message to the user!"])
```

## Advanced usage

You can customize the modal by giving it an optional configuration map.

We currently support these following configs:

- :shown -> a function called once the modal is shown.
- :hide -> a function called once the modal is asked to hide.
- :hidden -> a function called once the modal is hidden.

- :size -> Can be :lg (large) or :sm (small). Everything else defaults to medium.
- :keyboard -> if true, `esc' key can dismiss the modal. Default to true."
- :backdrop -> true (default): backdrop. 
               "static" : backdrop, but doesn't close the model when clicked upon.
               false : no backdrop.

```clj
:on-click #(reagent-modals/modal! [:div "some message to the user!"]
	                              {:size :lg}) ;  <----- will cause the modal to be 'large'
```

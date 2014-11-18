# reagent-modals

Bootstrap modal components for Reagent.

Takes care of all the little details for creating a modal window with Bootstrap.


## Install
<img src="https://raw.githubusercontent.com/Frozenlock/historian/master/modal-example.png"
 alt="Modal demo" title="Modal demo"/>

...

## Usage

Make sure you have the jQuery and Boostrap javascript loaded in your page:
```html
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
```


Include the `modal-window' component in the root of your document:

```clj
(defn main-page []
	[:div
	  [:h1 "Page title"]
	  [:span "Some content"]
	  [reagent-modals/modal-window]]) ;  <-------
```

Now, everytime you want to show a modal to the user, you just need to call `modal':

```clj
:on-click #(reagent-modals/modal [:div "some message to the user!"])
```

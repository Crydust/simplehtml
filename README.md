# simplehtml

A java html library inspired by [preact's h function](https://github.com/developit/preact/blob/master/src/h.js).

The only public class is "Html" and it contains static methods which allows us to write java code like this: 

```java
h("div", Map.of("id", "foo"),
  h("p", "Look, a simple html element!"),
  h("ul",
    h("li", h("p", "hello")),
    h("li", h("p", "there")),
    h("li", h("p", "people"))
  )
).toString();
```

To generate this html:

```html
<div id="foo">
  <p>Look, a simple html element!</p>
  <ul>
    <li><p>hello</p></li>
    <li><p>there</p></li>
    <li><p>people</p></li>
  </ul>
</div>
```

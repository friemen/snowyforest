# snowyforest

A Quil sketch designed to send [winter greetings](http://www.falkoriemenschneider.de/snowyforest) and say thanks.


## Usage

For interactive development start a REPL, and execute `(cljs-repl)`. 
Wait a few seconds until you see `<< started Weasel server on ws://127.0.0.1:9001 >>`
Open web/testindex.html with a browser, it will connect to the REPL.

In Emacs with CIDER you can execute a snippet like this

```
(define-key cider-mode-map (kbd "C-1")
  (lambda ()
          (interactive)
          (cider-eval-defun-at-point)
          (cider-interactive-eval "(do (def s (sketch.core/restart)) :ready)")))
```

to restart the sketch everytime you compile a toplevel expression.


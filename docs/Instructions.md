# Instructions on working with this git-book

All work on the book is performed in the `docs/` folder of the [ohnosequences/scala-guide](https://github.com/ohnosequences/scala-guide) repository.

## Prerequisites

These steps you need to do only once.

1. First install all the node.js things:

    ```bash
    npm install gitbook -g
    npm install gitbook-plugin-katex gitbook-plugin-richquotes
    ```

1. Clone the `gh-pages` branch in a separate folder

    Maybe there is a better solution for this, but here is a straightforward one:

    ```bash
    git clone --single-branch gh-pages ... _book
    ```

## Editing

* The structure of this book is in the [SUMMARY.md](SUMMARY.md) file
* It uses KaTeX plugin to render maths and it requires for some reason all math expressions to be in **double** dollar sings (even for inline math).


## Preview

```bash
gitbook serve
```

Then open in your browser [`http://localhost:4000`](http://localhost:4000). 
You can edit the content and once you save it the site will be updated automatically.


## Compiling and publishing the book

Once you're fine with the changes you've introduced 

1. Commit them if you're in the `master` branch, or merge if you were working in a separate pull-request

1. Compile the book:

    ```bash
    gitbook build
    ```

1. Now push result to the `gh-pages` branch:

    ```bash
    cd _book
    git commit -am "Updated the git-book content"
    git push origin gh-pages
    cd ..
    ```

# Instructions on working with this git-book

The general workflow:

- Every chapter is in it's own folder inside of `docs/` with `README.md` introduction and any related text in separate `.md` files
- The code samples are stored in `src/` folder (like a normal sbt-project)
- They will be transformed to markdown in `docs/src/...` by literator
- Then everything is linked by the `SUMMARY.md` file
- Built to the `_book/` folder and pushed to the `gh-pages` branch


## First-time preparation

These steps you need to do only once.

1. First install all the gitbook tools using [npm](https://github.com/npm/npm):

    ```bash
    npm install gitbook -g
    npm install gitbook-plugin-katex gitbook-plugin-richquotes gitbook-plugin-share
    ```

    Note, that it will create a local `node_modules/` folder which should be ignored by git.

1. Clone the `gh-pages` branch in a separate folder

    ```bash
    git clone --single-branch -b gh-pages https://github.com/ohnosequences/scala-guide.git _book
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

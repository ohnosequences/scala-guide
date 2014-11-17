# Instructions on working with this git-book

The general workflow:

- All the gitbook stuff is happening in the `docs/` folder
- Every chapter is in it's own folder inside of `docs/` with `README.md` introduction and any related text in separate `.md` files
- The code samples are stored in `src/` folder (like a normal sbt-project)
- They will be transformed to markdown in `docs/src/...` by literator
- Then everything is linked by the `SUMMARY.md` file
- Built to the `_book/` folder and pushed to the `gh-pages` branch

So you normally work with the sbt project, test code samples and then

```bash
sbt generateDocs
cd docs
gitbook serve
```

Then you edit other text, and publish it when it's fine.


## First-time preparation

These steps you need to do only once.

1. First install all the gitbook tools using [npm](https://github.com/npm/npm):

    ```bash
    npm install gitbook -g
    npm install gitbook-plugin-richquotes gitbook-plugin-toc
    ```

    Note, that it will create a local `node_modules/` folder which should be ignored by git.

1. Clone the `gh-pages` branch in a separate folder named `_book/`:

    ```bash
    git clone --single-branch -b gh-pages https://github.com/ohnosequences/scala-guide.git _book
    ```


## Editing

* The structure of this book is in the [SUMMARY.md](SUMMARY.md) file
* It seems that MathJax requires _all_ math expressions to be in **double** dollar sings (even for inline math)
  - There is a KaTeX plugin for gitbook which works 10x faster, but so far it doesn't [support](https://github.com/Khan/KaTeX/wiki/Function-Support-in-KaTeX) much Tex


## Config

**Very important**: `book.json` config cannot have comments. With comments gitbook doesn't complain, but it won't actually read this config.

### Plugins

* [Rich quotes](https://github.com/erixtekila/gitbook-plugin-richquotes)  
    You can use quotes with annotation, like `> **Info** Info` and it will be shown nicely in the result (see the link for the full list)
* [TOC](https://github.com/whzhyh/gitbook-plugin-toc)  
    You can add `<!-- toc -->` in you markdown texts to generate the table of content

Here is the [full list of the plugins](https://www.npmjs.org/search?q=gitbook-plugin) and here are some of them that I would add:

* [include](https://github.com/rlmv/gitbook-plugin-include): allows you include files one into another
* [quizzes](https://github.com/GitbookIO/plugin-quizzes): allows you to add simple quizzes
* [share](https://github.com/bguiz/gitbook-plugin-share): adds sharing buttons to the pages
* [punctuate](https://github.com/ErnWong/gitbook-plugin-punctuate): replaces things like `---` with a normal long-dash
* [disqus](https://github.com/GitbookIO/plugin-disqus): when there is something to comment
* [google analytics](https://github.com/GitbookIO/plugin-ga): when there is something to track
* [KaTeX](https://github.com/GitbookIO/plugin-katex): when it supports more TeX


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

You can use the `book.publish.sh` script for the last two steps.

gitbook build
rm -r _gh-pages/*
cp -r _book/* _gh-pages
cd _gh-pages
git add -A .
git commit -am "Updated the git-book content"
git push origin gh-pages
cd ..

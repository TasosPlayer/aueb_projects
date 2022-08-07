const express = require('express')
const path = require('path')
const app = express()
const port = 8080
const favorites = require("./models/favorites")
const handlebars = require('express-handlebars')
var cors = require('cors')

app.listen(port, function(error) {
    if(error){
        console.log('Something went wrong', error)
    } else {
        console.log('Server is listening on port ', port)
    }
})

app.engine(
    "handlebars",
    handlebars({
      layoutsDir: `${__dirname}/views/layouts`,
      defaultLayout: "main",

    })
  );
  app.set("view engine", "handlebars");

/* 
    Serve static content from directory "public",
    it will be accessible under path /static, 
    e.g. http://localhost:8080/static/index.html
*/
app.use('/', express.static(__dirname + '/public'))

app.use(cors())

// parse url-encoded content from body
app.use(express.urlencoded({ extended: false }))

// parse application/json content from body
app.use(express.json())


// handlebars.registerHelper('ckeckIfExists', function (id) {
//     return favorites.checkFav(id);
// })


//return new website with favorites
app.get ('/favorites',(req,res) =>{
    let favoriteList =favorites.findAll()
    res.render('list-favorites',{
        title:'Τα αγαπημενα μου:',
        // title:favoriteList[0].title,
        // author:favoriteList[0].author,
        // id:favoriteList[0].id
        favorites:favoriteList
    })

})
//return new website with the favorite you want to edit
app.get ('/favorites/edit',(req,res) =>{
    let bookToEdit= {
        title: req.body.title,
        author: req.body.author,
        id: parseInt(req.body.id)
    }
    console.log(bookToEdit);
    res.render('edit-favorite',{
        title:'Επεξεργασια:',
        // title:favoriteList[0].title,
        // author:favoriteList[0].author,
        // id:favoriteList[0].id
        bookToEdit
    })

})
//change 
app.put('favorites',(req,res) =>{
    let EditedBook= {
        title: req.body.title,
        author: req.body.author,
        id: parseInt(req.body.id),
        critic: req.body.critic
    }
    editFavorite(EditedBook);
    console.log(EditedBook);
    
})

//add to favorites
app.post('/favorites', (req,res) =>{
    const newBook = {
        title: req.body.title,
        author: req.body.author,
        id: parseInt(req.body.id)
    }
    var id = parseInt(req.body.id);
    
    //console.log("Adding: " +id);
    var checkIfExistsAlready = (favorites.findAll()).some(book => book.id === id);
    console.log(checkIfExistsAlready);
    if (checkIfExistsAlready){
        //console.log("Exists already");
        res.status(400).json("Exists already");
    }
    else{
        favorites.addToFavorites(newBook);
        //console.log("Added");
        res.json("Added");
    }
    //console.log(favorites.findAll());
})

// app.post('/favorites?id=wid&title=wtitle&author=wauthor', function(req,res){
//     // const title = req.body.title;
//     // const author = req.body.author;
//     // const id = parseInt(req.body.id);
//     var id = req.params.id;
//     var author = req.params.author;
//     var title = req.params.title;
//     console.log(id,author,title);
//     favorites.addToFavorites(title,author,id)
    
//     //console.log(favorites.findAll());
// })

//remove from favorites
app.delete('/favorites', (req,res) =>{
    const id = parseInt(req.body.id);
    //console.log(id);
    favorites.removeFromFavorites(id)
    res.status(200).json("Removed");
})

// //check if it is a favorite already
// app.get('/favorites/:id', (req,res) =>{
//     const id = parseInt(req.params.id);
//     const checkIfExistsAlready = favorites.checkFav(id);
    
//     if (checkIfExistsAlready)
//         res.json(checkIfExistsAlready);
//     else
//         res.json(checkIfExistsAlready);
// })

// serve index.html as content root
app.get('/', function(req, res){

    var options = {
        root: path.join(__dirname, 'public')
    }

    res.sendFile('index.html', options, function(err){
        console.log(err)
    })
})

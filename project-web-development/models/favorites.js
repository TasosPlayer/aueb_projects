var favorites = [];

// class Favorite {
//     constructor(title,author,id){
//         this.id=id;
//         this.author=author;
//         this.title=title;
//     }
// }
// function createFav(title,author,id){
//     return new Favorite(title,author,id)
// }

function addToFavorites(newBook) {
    //let f1 = createFav(title,author,id) //stoixeia Favorite
    favorites.push(newBook);

}

function editFavorite(book){
    const findIndex = favorites.findIndex(a => a.id === book.id);
    findIndex !== -1 && favorites.splice(findIndex , 1);
    favorites.push(book);
}

function removeFromFavorites(id){

    const findIndex = favorites.findIndex(a => a.id === id);
    findIndex !== -1 && favorites.splice(findIndex , 1);//remove the item at the position: findIndex
}

function findAll(){
    return favorites;
}

// function checkFav(id){
//     let checkIfExistsAlready = favorites.some(x => x.id === id);
//     if (checkIfExistsAlready) {
//         return checkIfExistsAlready;
//     } else {
//         return checkIfExistsAlready;
//     }
// }


module.exports = {
    // "create" : createFav,
    // "checkfav" : checkFav,
    "removeFromFavorites" : removeFromFavorites,
    "addToFavorites" : addToFavorites,
    "findAll" : findAll,
    "editFavorite": editFavorite
}
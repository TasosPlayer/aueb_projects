var searchResults = null;

function searchBooksOrAuthors(){
    let text = document.getElementById("search").value;
    fetch('https://reststop.randomhouse.com/resources/works?start=0&max=30&expandLevel=1&search='+text, {
        method:'GET',
        headers: {
            'Accept' : 'application/json'
        }
    })
    .then(response => response.json())
    .then(function (data) { 
        searchResults = data;
        //console.log(data);
        printResults()
    })
    .then()
    .catch(error =>{
        console.log(error);
    })

    
    return false;
}

function printResults() {
    var template = document.getElementById('search-results').innerHTML; //$('#basic-template1').html();    
    // Compile the template data into a function    
    var templateScript = Handlebars.compile(template);  
    
    // Define data in JSON format.    
    
    // Pass Data to template script. 
       
    var html = templateScript(searchResults);  
    console.log(searchResults)  ;  
    // Insert the HTML code into the page    
    document.getElementById('printed').innerHTML = html;
    return false;
}

 function show(){
    //  console.log(favorites);
    fetch('http://localhost:8080/favorites')
 }

 function add(title,author,id){
    
    fetch('http://localhost:8080/favorites', {
        method:'POST',
        headers:{
            'Accept' : 'application/json, text/plain',
            'Content-type' : 'application/json'
        }, 
        body: JSON.stringify({title:title, author: author, id: id })
    })
    .then(res =>res.json())
    .then(function (data) {
        console.log(data) 
        if(data=="Exists already")
            alert("This book is already added to your Favorites") ;
        else
            console.log("Added succesfully");
        //console.log(data)
        document.getElementById(id).className = "jsfav unfav";
        
        document.getElementById(id).onclick = () => remove1(title,author,id);
        document.getElementById(id).innerHTML = "remove";
    })
    .catch(error => {
        console.log(error);
    })
}
// function add(title,author,id){
//     var postheaders = new Headers();
//     fetch('http://localhost:8080/favorites?id=' + id + '&title=' + title + '&author=' + author, {
//         method:'POST',
//         headers: postheaders.append('Content-Type', 'application/json'), 
//     })
//     .then(res =>res.json()
//     )
//     .then(data => {
//         console.log('Success:', data);
//       })
//     .catch(error => {
//         console.log(error);
//     })
// }


function remove1(title,author,id){
    console.log(id);
   fetch('http://localhost:8080/favorites', {
    method:'DELETE',
    headers: {
        'Content-type' : 'application/json',
        'Accept' : 'application/json, text/plain'
    },
    body: JSON.stringify({title:title, author: author, id: id })
}) .then(response => response.json())
    .then(function (data) { 
        console.log(data);
        document.getElementById(id).className = "jsfav fav";
        document.getElementById(id).innerHTML="Favorite";
        document.getElementById(id).onclick = () => add(title,author,id);
        //document.getElementById("btn").addEventListener("mouseout ",show);
    })
    .catch(error =>{
        console.log(error);
    })
}
function remove2(id){
    console.log(id);
   fetch('http://localhost:8080/favorites', {
    method:'DELETE',
    headers: {
        'Content-type' : 'application/json',
        'Accept' : 'application/json, text/plain'
    },
    body: JSON.stringify({ id: id })
}) .then(response => response.json())
    .then(function (data) { 
        console.log(data);
        //document.getElementById("btn").addEventListener("mouseout ",show);
    })
    .catch(error =>{
        console.log(error);
    })
}


function goToEditPage(title,author,id){
    console.log(title,author,id);
    fetch('http://localhost:8080/favorites/edit', {
        method:'GET',
        headers:{
            'Accept' : 'application/json, text/plain',
            'Content-type' : 'application/json'
        }, 
        body: JSON.stringify({title:title, author: author, id: id })
    })
    .then(res =>res.json())
    .then(function (data) {
        console.log(data) 
       
       
    })
    .catch(error => {
        console.log(error);
    })
}

function edit(title,author,id,critic){
    
    fetch('http://localhost:8080/favorites', {
        method:'PUT',
        headers:{
            'Accept' : 'application/json, text/plain',
            'Content-type' : 'application/json'
        }, 
        body: JSON.stringify({title:title, author: author, id: id, critic: critic })
    })
    .then(res =>res.json())
    .then(function (data) {
        console.log(title,author,id,critic) 
        
        //console.log(data)
       
    })
    .catch(error => {
        console.log(error);
    })
}
// Handlebars.registerHelper('ckeckIfExists', function (value) {
//     fetch('http://localhost:8080/favorites/:'+value, {
//     method:'GET',
//     headers: {
//         'Accept' : 'application/json'
//     }
// })  .then(response => response.json())
//     .then(function (data) { 
//         let exists = data;
//         if(exists){
//             return true;
//         }
//         else{
//             return false;
//         }
//     })
//     .catch(error =>{
//         console.log(error);
//     })
// });




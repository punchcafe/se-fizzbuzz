console.log("Hello World!");

function FizzBuzzEntity(fizzBuzzJson){
    this.id = fizzBuzzJson["id"]
    this.value = fizzBuzzJson["value"]
    this.isFavourite = fizzBuzzJson["is_favourite"]
}

var controlPanelState = {
    pageData: null
}

FizzBuzzEntity.prototype.render = function(){
    console.log(this)
    return `<div class="fizz_buzz_entity" id="fizz_buzz_id_${this.id}">
                <div class="fizz_buzz_id_container">${this.id}</div>
                <div class="fizz_buzz_value_container">${this.value}</div>
                <div class="favourite_icon_${this.isFavourite ? "active" : "inactive" }"></div>
            </div>`
}

function renderFizzBuzzes(fizzBuzzList){
    var renderedHtml = ""
    console.log(fizzBuzzList)
    for(var i = 0; i < fizzBuzzList.length; i++){
        renderedHtml = renderedHtml.concat(new FizzBuzzEntity(fizzBuzzList[i]).render())
    }
    return renderedHtml
}

async function getPageData(pageSize,PageNumber){
    var response = fetch(`http://localhost:4000/fizzbuzz?page_size=${pageSize}&page_number=${PageNumber}`, {
    method: 'GET',
    mode: 'cors', 
    credentials: 'same-origin',
    referrerPolicy: 'origin'})
    .then(response => response.json())
    var returnValue = await response;
    return returnValue
}

function setPageData(pageDataJson){
    controlPanelState.pageData = {
        hasPrevious: pageDataJson["has_previous_page"],
        hasNext: pageDataJson["has_next_page"],
        pageNumber: pageDataJson["page_number"],
        pageSize: pageDataJson["page_size"]
    }
}

window.addEventListener('load', (event) => {
    const initialPage = getPageData(50,1)
    initialPage.then(response => renderFizzBuzzes(response["data"]))
                .then(result => document.getElementById("fizz_buzz_container").innerHTML = result)
    initialPage.then(response => response["page"])
                .then(result => setPageData(result))
})

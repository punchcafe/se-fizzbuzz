function FizzBuzzEntity(fizzBuzzJson){
    this.id = fizzBuzzJson["id"]
    this.value = fizzBuzzJson["value"]
    this.isFavourite = fizzBuzzJson["is_favourite"]
}

const fizzBuzzIdFormat = /fizz_buzz_id_(\d+)/
const fizzBuzzFavouriteClassFormat = /favourite_icon_(active|inactive)/

var controlPanelState = {
    // startup with true
    actionInProgress: true,
    userPref: {
        pageSize: null,
        pageNumber: 1
    },
    pageData: null
}

FizzBuzzEntity.prototype.render = function(){
    return `<div class="fizz_buzz_entity" id="fizz_buzz_id_${this.id}">
                <div class="fizz_buzz_touch_event_captor"></div>
                <div class="fizz_buzz_id_container">${this.id}</div>
                <div class="fizz_buzz_value_container">${this.value}</div>
                <div class="favourite_icon_${this.isFavourite ? "active" : "inactive" }"></div>
            </div>`
}

function renderFizzBuzzes(fizzBuzzList){
    var renderedHtml = ""
    for(var i = 0; i < fizzBuzzList.length; i++){
        renderedHtml = renderedHtml.concat(new FizzBuzzEntity(fizzBuzzList[i]).render())
    }
    return renderedHtml
}

async function getPageData(PageNumber){
    const response = fetch(`http://localhost:4000/fizzbuzz?page_size=${controlPanelState.userPref.pageSize}&page_number=${PageNumber}`, {
    method: 'GET',
    mode: 'cors', 
    credentials: 'same-origin',
    referrerPolicy: 'origin'})
    .then(response => response.json())
    var returnValue = await response;
    return returnValue
}

async function setIsFavourite(fizBuzzId, isFavourite){
    const response = fetch(`http://localhost:4000/fizzbuzz/${fizBuzzId}`, {
        method: 'PUT',
        mode: 'cors', 
        credentials: 'same-origin',
        headers: {
            "Content-Type" : "application/json"
        },
        referrerPolicy: 'origin',
        body: JSON.stringify({is_favourite: isFavourite})
    }).then(response => response.json())
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

function getCurrentIsFavouriteStatus(containerElement){
    for (var i = 0; i < containerElement.childNodes.length; i++) {
        const matches = containerElement.childNodes[i].className 
            && containerElement.childNodes[i].className.match(fizzBuzzFavouriteClassFormat)
        if(matches){
            if(matches[1] == "inactive"){
                return false
            }
            return true
        }   
    }
}

function assignFavouriteCallbacks(fizzBuzzNodes){
    for(var i = 0; i<fizzBuzzNodes.length; i++){
        fizzBuzzNodes[i].addEventListener('click', (event) => {
            if(controlPanelState.actionInProgress){
                return
            }
            controlPanelState.actionInProgress = true
            const parentNode = event.target.parentNode
            console.log(parentNode)
            // Captor div will have been clicked, so get informtaion from parent
            var fizzBuzzElementId = parentNode.id
            const fizzBuzzId = fizzBuzzElementId.match(fizzBuzzIdFormat)[1];
            const currentFavouriteStatus = getCurrentIsFavouriteStatus(parentNode)
            setIsFavourite(fizzBuzzId, !currentFavouriteStatus)
            .then(result => triggerPageRender())
        })
    }
}

function triggerPageRender(){
    const initialPage = getPageData(1)
    initialPage.then(response => renderFizzBuzzes(response["data"]))
                .then(result => document.getElementById("fizz_buzz_container").innerHTML = result)
    initialPage.then(response => response["page"])
                .then(result => setPageData(result))
    initialPage.then(response => renderFizzBuzzes(response["data"]))
                .then(result => assignFavouriteCallbacks(document.getElementById("fizz_buzz_container").childNodes))
    initialPage.then(response => controlPanelState.actionInProgress = false)
}

function getPreviousSelectionIndex(){
    const selectorOptions = document.getElementById("page_size_selector").options
    for(var i = 0; i < selectorOptions.length; i++){
        if(parseInt(selectorOptions[i].value) == controlPanelState.userPref.pageSize){
            return i
        }
    }
}

window.addEventListener('load', (event) => {
    const selectedPageSizeOptions = document.getElementById("page_size_selector").options 
    controlPanelState.userPref.pageSize = parseInt(selectedPageSizeOptions[selectedPageSizeOptions.selectedIndex].value)
    document.getElementById("page_size_selector").addEventListener("change", (event) => {
        if(controlPanelState.actionInProgress){
            target.options.selectedIndex = getPreviousSelectionIndex()
            return
        }
        controlPanelState.actionInProgress = true;
        controlPanelState.userPref.pageSize = parseInt(event.target.options[event.target.options.selectedIndex].value)
        triggerPageRender()
    })
    triggerPageRender()
})

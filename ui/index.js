console.log("Hello World!");

function FizzBuzzEntity(id, value, isFavourite){
    this.id = id
    this.value = value
    this.isFavourite = isFavourite
}

var controlPanelState = {

}

FizzBuzzEntity.prototype.render = function(){
    return `<div class="fizz_buzz_entity" id="fizz_buzz_id_${this.id}">
                <div class="fizz_buzz_id_container">${this.id}</div>
                <div class="fizz_buzz_value_container">${this.value}</div>
                <div class="favourite_icon_${this.isFavourite ? "active" : "inactive" }"></div>
            </div>`
}

fetch('http://localhost:4000/fizzbuzz', {
    method: 'GET',
    mode: 'cors', 
    credentials: 'same-origin',
    referrerPolicy: 'origin'})
  .then(response => console.log(response))
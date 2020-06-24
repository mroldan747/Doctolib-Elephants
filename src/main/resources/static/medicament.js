
let tag = document.getElementsByClassName("tag");

for(let i = 0; i < tag.length; i++) {
    tag[i].onclick = function () {
        let circle = this.getElementsByTagName("div");
        if (circle[0].className === "circle circle-red") {
            circle[0].classList.remove("circle-red");
            circle[0].classList.add("circle-orange");
        } else if (circle[0].className === "circle circle-orange") {
            circle[0].classList.remove("circle-orange");
            circle[0].classList.add("circle-green");
        } else if (circle[0].className === "circle circle-green") {
            circle[0].classList.remove("circle-green");
            circle[0].classList.add("circle-red");
        }
    }
}
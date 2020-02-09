let card = null;
let end = 0;
let won;
function apportionment() {
    let $el = $('#baraja-el'),
        baraja = $el.baraja();

    if (card === null) {
        baraja.fan({
            speed: 500,
            easing: 'ease-out',
            range: 320,
            direction: 'right',
            origin: {
                x: 50,
                y: 200
            },
            center: true,

        });
    }
}


function endGame() {
    end += 1;
    if (end === 2) {
        if (won !== undefined) {
            if (won) {
                alert("win");
            } else {
                alert("loose");
            }
            finishGame();
        }
    }
}

function getCard() {
    console.log(card);
    return card;
}

function setForehead(forehead) {
    if (forehead !== undefined) {
        forehead.forEach(function (item) {
            className = `<div class="forehead" ><a href="#" class="` + item['lear'] + ` ` + item['par'] + `"</div>`;
            $(".deck").append(className);
        });
    }
}

function setSonic(sonic) {
    if (sonic !== undefined) {
        sonic.forEach(function (item) {
            className = `<div class="sonic" ><a href="#" class="` + item['lear'] + ` ` + item['par'] + `"</div>`;
            $(".deck").append(className);
        });

    }
}

function setCard(mainCard) {
    if (mainCard !== undefined) {
        console.log(mainCard);
        card = mainCard;
    }
}


function isWon(win) {
    console.log(win);
    if (win !== undefined) {
        won = win;
    }
}


function buildCard(lear, par) {
    return {
        lear: lear,
        par: par,

    };
}


function setMainCard(lear, par) {
    if (card === null) {
        card = buildCard(lear, par)
    }
}

function checkJackpot(form) {
    if (form.jackpot.value > 0)
        return true;
    else return false;
}



function check(form) {
    if (checkJackpot(form) && card !== null) {
        document.getElementById("cardInput").value = JSON.stringify(card);
        console.log(card);
        return true;
    } else if (card === null) {
        alert("Выберите карту")
    }
    return false;
}

function finishGame(){
var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
 console.log(this.responseText);

    }
    else
            {
            console.log(xhttp.readyState);
              console.log(xhttp.status);
                console.log('Something is wrong !!');
            }
  };

  xhttp.open("POST", "http://localhost:9080/MainServlet", true);
  xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhttp.send({command :"finishGame"});


  }
let card = null;
let end = 0;
let won;
let zOne = 100;
let zTwo = 100;

function reverse() {
    card = null;
    end = 0;
    won = undefined;
    apportionment();
    $('.sonic').remove();
    $('.forehead').remove();
}

function apportionment() {
    if (card === null) {
    let $el = $('#baraja-el'),
                                baraja = $el.baraja();
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

function refreshMainCard(){
if(card!==null)
 $(".baraja-container").append(`<li style="z-index: 2000"><a href="#" class="` + card['lear'] + ` ` + card['par'] + `"></li>`);
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


function getZOne(){
zOne-=1;
return zOne
}
function getZTwo(){
zTwo-=1;
return zTwo
}
function setForehead(forehead) {
    if (forehead !== undefined) {
    forehead.reverse();

        forehead.forEach(function (item) {
            className = `<div class="forehead" style="z-index: `+getZOne()+`"><a href="#" class="` + item['lear'] + ` ` + item['par'] + `" ></div>`;
            $(".deck").append(className);
        });
    }
}

function setSonic(sonic) {
    if (sonic !== undefined) {
    sonic.reverse();

        sonic.forEach(function (item) {

            className = `<div class="sonic" style="z-index: `+getZTwo()+`"><a href="#" class="` + item['lear'] + ` ` + item['par'] + `"</div>`;
            $(".deck").append(className);
        });

    }
}

function setCard(mainCard) {
    if (mainCard !== undefined) {

        card = mainCard;
        $(".baraja-container").append(`<li style="z-index: 2000"><a href="#" class="` + mainCard['lear'] + ` ` + mainCard['par'] + `"></li>`);
    }
}


function isWon(win) {
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
        card = buildCard(lear, par);
        console.log(card);
    }
}

function checkJackpot(form) {
    return form.jackpot.value > 0;
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

function finishGame() {
    $.ajax({
        url: 'http://localhost:8085/MagicServlet/',
        data: {
            command: "finishGame"
        },
        success: function (responseText) {
            console.log(responseText);
            reverse();
        }
    });

}

function init() {

    var animateLeftb = anime({
        targets: '.forehead',
        left: '55%',
        top: '20%',
        autoplay: true,
        delay: function (target, index, targetCount) {
            return (targetCount - index) * 2000;
        },
        easing: 'easeInOutSine',
        complete: function () {
            endGame();
        }

    });
    var animateLeftr = anime({
        targets: '.sonic',
        left: '40%',
        top: '20%',
        autoplay: true,
        delay: function (target, index, targetCount) {
            return (targetCount - index) * 2000;
        },
        easing: 'easeInOutSine',
        complete: function () {
            endGame();
        }
    });
    animateLeftb.restart;
    animateLeftr.restart;

}
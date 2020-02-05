
<head>
    <meta charset="UTF-8" />
    <title>Gameplay</title>

  <style>
    <%@include file="/css/baraja.css"%>
    <%@include file="/css/demo.css"%>
    <%@include file="/css/custom.css"%>
    <%@include file="/css/style.css"%>
    </style>
    <script>
     <%@include file="/js/jquery-1.11.2.min.js"%>
     <%@include file="/js/modernizr.custom.79639.js"%>
     <%@include file="/js/jquery.min.js"%>
     <%@include file="/js/jquery.baraja.js"%>

     <%@include file="/js/anime.min.js"%>
     <%@include file="/js/game.js"%>
    </script>
    <script>

      window.onload=function(){
      setCard(${requestScope.mainCard});
      setForehead(${requestScope.forehead});
      setSonic(${requestScope.sonic});
      apportionment();
       isWon(${requestScope.win});
      var animateLeftb = anime({
        targets: '.forehead',
        left: 400,
        top: 400,
        autoplay: true,
        delay: function(target, index, targetCount) {
          return (targetCount - index) * 2000;
        },
        easing: 'easeInOutSine',
         complete: function(){
                 endGame();
                        }

      });
      var animateLeftr = anime({
        targets: '.sonic',
        left: 600,
        top: 400,
        autoplay: true,
        delay: function(target, index, targetCount) {
          return (targetCount - index) * 2000;
        },
        easing: 'easeInOutSine',
         complete: function(){
         endGame();
                }
      });
      animateLeftb.restart;
      animateLeftr.restart;

      }
      </script>
</head>
<body >
    <form onsubmit="check(this)" action="MainServlet" method="post">
        <input type="text" hidden name="command" value="startGame" />
        <input type="text" hidden name="card" id="cardInput" />
        <input required type="number"name="jackpot">
        <input type="submit">
        </form>
        <div id="anime-demo" class="deck">
        </div>
    <div class="baraja-demo">

        <ul id="baraja-el" class="baraja-container">
            <li> <a href="#" class="spades two" onclick="setMainCard('spades','two')"></a></li>
            <li> <a href="#" class="spades three" onclick="setMainCard('spades','three')"></a></li>
            <li> <a href="#" class="spades four" onclick="setMainCard('spades','four')"></a></li>
            <li> <a href="#" class="spades five" onclick="setMainCard('spades','five')"></a></li>
            <li> <a href="#" class="spades six" onclick="setMainCard('spades','six')"> </a></li>
            <li> <a href="#" class="spades seven" onclick="setMainCard('spades','seven')"></a></li>
            <li> <a href="#" class="spades eight" onclick="setMainCard('spades','eight')"></a></li>
            <li> <a href="#" class="spades nine" onclick="setMainCard('spades','nine')"></a></li>
            <li> <a href="#" class="spades ten" onclick="setMainCard('spades','ten')"></a></li>
            <li> <a href="#" class="spades jack" onclick="setMainCard('spades','jack')"></a></li>
            <li> <a href="#" class="spades queen" onclick="setMainCard('spades','queen')"></a></li>
            <li> <a href="#" class="spades king" onclick="setMainCard('spades','king')"></a></li>
            <li> <a href="#" class="spades ace" onclick="setMainCard('spades','ace')"></a></li>

            <li> <a href="#" class="heart two" onclick="setMainCard('heart','two')"></a></li>
            <li> <a href="#" class="heart three" onclick="setMainCard('heart','three')"></a></li>
            <li> <a href="#" class="heart four" onclick="setMainCard('heart','four')"></a></li>
            <li> <a href="#" class="heart five" onclick="setMainCard('heart','five')"></a></li>
            <li> <a href="#" class="heart six" onclick="setMainCard('heart','six')"></a></li>
            <li> <a href="#" class="heart seven" onclick="setMainCard('heart','seven')"></a></li>
            <li> <a href="#" class="heart eight" onclick="setMainCard('heart','eight')"></a></li>
            <li> <a href="#" class="heart nine" onclick="setMainCard('heart','nine')"></a></li>
            <li> <a href="#" class="heart ten" onclick="setMainCard('heart','ten')"></a></li>
            <li> <a href="#" class="heart jack" onclick="setMainCard('heart','jack')"></a></li>
            <li> <a href="#" class="heart queen" onclick="setMainCard('heart','queen')"></a></li>
            <li> <a href="#" class="heart king" onclick="setMainCard('heart','king')"></a></li>
            <li> <a href="#" class="heart ace" onclick="setMainCard('heart','ace')"></a></li>

            <li> <a href="#" class="clubs two" onclick="setMainCard('clubs','two')"></a></li>
            <li> <a href="#" class="clubs three" onclick="setMainCard('clubs','three')"></a></li>
            <li> <a href="#" class="clubs four" onclick="setMainCard('clubs','four')"></a></li>
            <li> <a href="#" class="clubs five" onclick="setMainCard('clubs','five')"></a></li>
            <li> <a href="#" class="clubs six" onclick="setMainCard('clubs','six')"></a></li>
            <li> <a href="#" class="clubs seven" onclick="setMainCard('clubs','seven')"></a></li>
            <li> <a href="#" class="clubs eight" onclick="setMainCard('clubs','eight')"></a></li>
            <li> <a href="#" class="clubs nine" onclick="setMainCard('clubs','nine')"></a></li>
            <li> <a href="#" class="clubs ten" onclick="setMainCard('clubs','ten')"></a></li>
            <li> <a href="#" class="clubs jack" onclick="setMainCard('clubs','jack')"></a></li>
            <li> <a href="#" class="clubs queen" onclick="setMainCard('clubs','queen')"></a></li>
            <li> <a href="#" class="clubs king" onclick="setMainCard('clubs','king')"></a></li>
            <li> <a href="#" class="clubs ace" onclick="setMainCard('clubs','ace')"></a></li>

            <li> <a href="#" class="diamonds two" onclick="setMainCard('diamonds','two')"></a></li>
            <li> <a href="#" class="diamonds three" onclick="setMainCard('diamonds','three')"></a></li>
            <li> <a href="#" class="diamonds four" onclick="setMainCard('diamonds','four')"></a></li>
            <li> <a href="#" class="diamonds five" onclick="setMainCard('diamonds','five')"></a></li>
            <li> <a href="#" class="diamonds six" onclick="setMainCard('diamonds','six')"></a></li>
            <li> <a href="#" class="diamonds seven" onclick="setMainCard('diamonds','seven')"></a></li>
            <li> <a href="#" class="diamonds eight" onclick="setMainCard('diamonds','eight')"></a></li>
            <li> <a href="#" class="diamonds nine" onclick="setMainCard('diamonds','nine')"></a></li>
            <li> <a href="#" class="diamonds ten" onclick="setMainCard('diamonds','ten')"></a></li>
            <li> <a href="#" class="diamonds jack" onclick="setMainCard('diamonds','jack')"></a></li>
            <li> <a href="#" class="diamonds queen" onclick="setMainCard('diamonds','queen')"></a></li>
            <li> <a href="#" class="diamonds king" onclick="setMainCard('diamonds','king')"></a></li>
            <li> <a href="#" class="diamonds ace" onclick="setMainCard('diamonds','ace')"></a></li>

        </ul>
    </div>
    </section>

    </div>


</body>
<!--
2 – two – second (два – второй);
3 – three – third (три – третий);
4 – four – fourth (четыре – четвертый);
5 – five – fifth (пять – пятый);
6 – six – sixth (шесть – шестой);
7 – seven – seventh (семь – седьмой);
8 – eight – eighth (восемь – восьмой);
9 – nine – ninth (девять – девятый);
10 – ten – tenth (десять – десятый);
валет - jack
королева - queen
король - king
туз - ace
*/-->
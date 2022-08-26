const boxImgA = document.getElementById('box-a-img');
const boxImgB = document.getElementById('box-b-img');
const boxTempA = document.getElementById('box-a-temp');
const boxTempB = document.getElementById('box-b-temp');

fetch("https://api.openweathermap.org/data/2.5/weather?q=Plovdiv&appid=91dd5b378c11ad15a40d2ad98c04e21e")
.then(data => data.json())
.then(info => {
    console.log(info);

    boxTempA.innerText = Math.round(info.main.temp - 273.15);
    boxImgA.src = '/images/weather-icons/' + info.weather[0].icon + '.png';
})

fetch("https://api.openweathermap.org/data/2.5/weather?q=Smolyan&appid=91dd5b378c11ad15a40d2ad98c04e21e")
    .then(data => data.json())
    .then(info => {
        console.log(info);

        boxTempB.innerText = Math.round(info.main.temp - 273.15);
        boxImgB.src = '/images/weather-icons/' + info.weather[0].icon + '.png';
    })


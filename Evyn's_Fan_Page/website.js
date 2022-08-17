'use strict';

function colorChange() {
    document.body.classList.toggle('light-theme');
    document.body.classList.toggle('dark-theme');
    
    const Name = document.body.className;
    if(Name == "light-theme")
        this.textContent = "Dark";
    else
        this.textContent = "Light";
}
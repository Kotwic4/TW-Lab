let waterfall = require("async/waterfall");

let forkWait = 0;
let condWait = 0;

let Fork = function() {
    this.state = 0;
    return this;
};

Fork.prototype.acquire = function(cb) {
    // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
    //    i ponawia probe itd.
    let wait = function (cb, waitTime, fork) {
        forkWait += waitTime;
        if(fork.state === 0){
            fork.state = 1;
            cb();
        }
        else{
            setTimeout(function() {wait(cb,waitTime*2,fork);}, waitTime);
        }
    };
    let initWaitTime = 1;
    forkWait+=initWaitTime;
    let fork = this;
    setTimeout(function() {wait(cb,initWaitTime*2,fork);}, initWaitTime);
};

Fork.prototype.release = function() {
    this.state = 0;
};

let Conductor = function(n) {
    this.state = n-1;
    return this;
};

Conductor.prototype.acquire = function(cb) {
    // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
    //    i ponawia probe itd.
    let wait = function (cb, waitTime, cond) {
        condWait += waitTime;
        if(cond.state > 0){
            cond.state--;
            cb();
        }
        else{
            setTimeout(function() {wait(cb,waitTime*2,cond);}, waitTime);
        }
    };
    let initWaitTime = 1;
    let cond = this;
    condWait+= initWaitTime;
    setTimeout(function() {wait(cb,initWaitTime*2,cond);}, initWaitTime);
};

Conductor.prototype.release = function() {
    this.state++;
};

let Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    return this;
};

Philosopher.prototype.startNaive = function(count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    // zaimplementuj rozwiazanie naiwne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
    task = [];

    let jedz = function(cb,fork1, fork2,id){
        setTimeout(function(){
            fork1.release();
            fork2.release();
            console.log("Oddalem widelce " + id);
            cb()
        },random(0,0));
    };

    for(let i = 0; i < count; i++){
        task.push(function(cb){setTimeout(cb,random(0,5));});
        task.push(function(cb){console.log("Chce lewy widelec " + id); cb()});
        task.push(function(cb){forks[f1].acquire(cb)});
        task.push(function(cb){console.log("Mam lewy widelec " + id); cb()});
        task.push(function(cb){console.log("Chce prawy widelec " + id); cb()});
        task.push(function(cb){forks[f2].acquire(cb)});
        task.push(function(cb){console.log("Mam prawy widelec " + id); cb()});
        task.push(function (cb){ jedz(cb,forks[f1],forks[f2],id)} );
    }
    waterfall(task,function(err,result){console.log("done " + id);})
};

function random (low, high) {
    return Math.random() * (high - low) + low;
}


Philosopher.prototype.startAsym = function(count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    // zaimplementuj rozwiazanie asymetryczne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow

    if(id % 2 === 1){
        f2 = this.f1;
        f1 = this.f2;
    }

    task = [];

    let jedz = function(cb,fork1, fork2,id){
        setTimeout(function(){
            fork1.release();
            fork2.release();
            console.log("Oddalem widelce " + id);
            cb()
        },random(0,0));
    };

    for(let i = 0; i < count; i++){
        task.push(function(cb){setTimeout(cb,random(0,5));});
        task.push(function(cb){console.log("Chce lewy widelec " + id); cb()});
        task.push(function(cb){forks[f1].acquire(cb)});
        task.push(function(cb){console.log("Mam lewy widelec " + id); cb()});
        task.push(function(cb){console.log("Chce prawy widelec " + id); cb()});
        task.push(function(cb){forks[f2].acquire(cb)});
        task.push(function(cb){console.log("Mam prawy widelec " + id); cb()});
        task.push(function (cb){ jedz(cb,forks[f1],forks[f2],id)} );
    }
    waterfall(task,function(err,result){
        console.log("done " + id);
        console.log("fork " + forkWait);
        console.log("cond " + condWait);})
};

let N = 10;
let cond = new Conductor(N);

Philosopher.prototype.startConductor = function(count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    // zaimplementuj rozwiazanie z kelnerem
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow

    task = [];

    let jedz = function(cb,fork1, fork2,id){
        setTimeout(function(){
            fork1.release();
            fork2.release();
            console.log("Oddalem widelce " + id);
            cb()
        },random(0,0));
    };

    for(let i = 0; i < count; i++){
        task.push(function(cb){setTimeout(cb,random(0,5));});
        task.push(function(cb){cond.acquire(cb)});
        task.push(function(cb){console.log("Chce lewy widelec " + id); cb()});
        task.push(function(cb){forks[f1].acquire(cb)});
        task.push(function(cb){console.log("Mam lewy widelec " + id); cb()});
        task.push(function(cb){console.log("Chce prawy widelec " + id); cb()});
        task.push(function(cb){forks[f2].acquire(cb)});
        task.push(function(cb){console.log("Mam prawy widelec " + id); cb()});
        task.push(function (cb){ jedz(cb,forks[f1],forks[f2],id)} );
        task.push(function (cb){ cond.release(); cb()} );
    }
    waterfall(task,function(err,result){
        console.log("done " + id);
        console.log("fork " + forkWait);
        console.log("cond " + condWait);})
};

let forks = [];
let philosophers = [];
for (let i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (let i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

// for (let i = 0; i < N; i++) {
//     philosophers[i].startNaive(10);
// }

// for (let i = 0; i < N; i++) {
//     philosophers[i].startAsym(10);
// }

for (let i = 0; i < N; i++) {
    philosophers[i].startConductor(10);
}
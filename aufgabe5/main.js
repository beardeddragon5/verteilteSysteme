'use strict';

const EventEmitter = require('events');

class Process extends EventEmitter {
  constructor(pid, active = true) {
    super();
    this.pid = pid;
    this.active = active;
    this.count = 0;
  }

  toString() {
    return `p(${this.pid}, ${this.active ? 'active': 'inactive'})`;
  }
}

const processes = [];
const processCount = process.env.PROCESSES || 10;
const processToKill = process.env.KILL || 9;
const processToElect = process.env.ELECT || 0;

for (let i = 0; i < processCount; i++) {
  processes.push(new Process(i));
}

function appendListener(p) {
  p.on('elect', sender => {
    if (!p.active) return;

    if (sender) {
      sender.emit('ok', p);
      console.log("OK-Nachricht von Prozess %d an Prozess %d gesendet", p.pid, sender.pid);
    }

    let failedRequest = 0;
    let totalRequests = 0;

    for (let j = p.pid+1; j < processes.length; j++) {
      totalRequests++;
      p[`elect${processes[j].pid}`] = setTimeout(() => {
        failedRequest++;
        if (failedRequest === totalRequests) {
          processes.forEach(pr => {
            pr.emit('coord', p);
            console.log("COORD-Nachricht von Prozess %d an Prozess %d gesendet", p.pid, pr.pid);
          });
        }
      }, 100);

      processes[j].emit('elect', p);
      console.log("ELECT-Nachricht von Prozess %d an Prozess %d gesendet", p.pid, processes[j].pid);
    }
  });

  p.on('ok', sender => {
    if (!p.active) return;
    clearTimeout(p[`elect${sender.pid}`]);
    delete p[`elect${sender.pid}`];
  });

  p.on('coord', sender => {
    if (!p.active) return;
  });
}

for (let i = 0; i < processCount; i++) {
  appendListener(processes[i]);
}

processes[processToKill].active = false;
processes[processToElect].emit('elect');

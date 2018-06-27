
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#define PROCESSES 10

typedef struct {
  int pid;
  bool active;
} process_t;

void process_coord(const process_t p, const process_t processes[PROCESSES]) {
  for (int pid = 0; pid < PROCESSES; pid++) {
    if (processes[pid].active) {
      printf("COORD-Nachricht von Prozess %d an Prozess %d gesendet\n", p.pid, pid);
    }
  }
}

bool process_elect(const process_t p, const process_t processes[PROCESSES], int sender) {
  if (!p.active) {
    return false;
  }
  if (sender != -1) {
    printf("OK-Nachricht von Prozess %d an Prozess %d gesendet\n", p.pid, sender);
  }
  bool receivedOk = false;
  for (int pid = p.pid + 1; pid < PROCESSES; pid++) {
    printf("ELECT-Nachricht von Prozess %d an Prozess %d gesendet\n", p.pid, pid);
    if (process_elect(processes[pid], processes, p.pid)) {
      receivedOk = true;
    }
  }

  if (!receivedOk) {
    process_coord(p, processes);
  }
  return true;
}

int main(int argc, char** argv) {
  process_t processes[PROCESSES];
  for (int i = 0; i < PROCESSES; i++) {
    processes[i].pid = i;
    processes[i].active = true;
  }

  if (argc < 3) {
    printf("%s <processToKill> <processToElect>\n", argv[0]);
    return EXIT_FAILURE;
  }

  const int userDefinedProcessToKill = atoi(argv[1]);
  const int userDefinedProcessToStartElect = atoi(argv[2]);

  if (userDefinedProcessToKill < 0 || userDefinedProcessToKill > PROCESSES) {
    fprintf(stderr, "process to kill must be in interval [0, %d]\n", PROCESSES);
    return EXIT_FAILURE;
  }
  if (userDefinedProcessToStartElect < 0 || userDefinedProcessToStartElect > PROCESSES) {
    fprintf(stderr, "process for elections must be in interval [0, %d]\n", PROCESSES);
    return EXIT_FAILURE;
  }

  processes[userDefinedProcessToKill].active = false;

  printf("kill: %d, elect: %d\n", userDefinedProcessToKill, userDefinedProcessToStartElect);

  process_elect(processes[userDefinedProcessToStartElect], processes, -1);

  return EXIT_SUCCESS;
}

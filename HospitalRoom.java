package edu.neu.info5100;

public class HospitalRoom {
    //TODO: implement your code here
    volatile static int doctorEnters = 0;
    volatile static int patientEnters = 0;
    static int ALL_DOCTORS = 1;
    static int ALL_PATIENTS = 1;

    private final Object LOCK_1 = new Object();
    private final Object LOCK_2 = new Object();

    public boolean doctorEnter(Doctor d) throws InterruptedException {
        //TODO: implement your code here
        synchronized (LOCK_1) {
            if (ALL_DOCTORS != 1) {
                doctorEnters += 1;
                System.out.printf("Doctor %s Entered, number of doctor %s%n", d.name, doctorEnters);
                LOCK_1.notify();
                return true;
            } else {
                System.out.printf("Doctor %s is waiting to Enter, number of doctor %s%n", d.name, doctorEnters);
                LOCK_1.wait();
                return false;
            }
        }
    }

    public boolean doctorLeave(Doctor d) throws InterruptedException {
        //TODO: implement your code here
        synchronized (LOCK_1) {
            if (doctorEnters != 0) {
                doctorEnters -= 1;
                System.out.printf("Doctor %s Left, number of doctor %s%n", d.name, doctorEnters);
                LOCK_1.notify();
                return true;
            } else {
                LOCK_1.wait();
                return false;
            }
        }
    }

    public boolean patientEnter(Patient p) throws InterruptedException {
        //TODO: implement your code here
        synchronized (LOCK_2) {
            if (patientEnters != ALL_PATIENTS) {
                patientEnters += 1;
                System.out.printf("Patient %s entered, number of patients %s%n", p.name, doctorEnters);
                LOCK_2.notify();
                return true;
            } else {
                System.out.printf("Doctor %s is waiting to Enter, number of doctor %s%n", p.name, doctorEnters);
                LOCK_2.wait();
                return false;
            }
        }
    }

    public boolean patientLeave(Patient p) throws InterruptedException {
        //TODO: implement your code here
        synchronized (LOCK_2) {
            if (patientEnters != 0) {
                patientEnters -= 1;
                System.out.printf("Patient %s left%n", p.name);
                LOCK_2.notify();
                return true;
            } else {
                LOCK_2.wait();
                return false;
            }
        }
    }

}

class Doctor {
    public String name;

    public Doctor(String name) {
        this.name = name;
    }
}

class Patient {
    public String name;

    public Patient(String name) {
        this.name = name;
    }
}

class Main2 {
    public static void main(String[] args) {
        HospitalRoom room = new HospitalRoom();
        Doctor siva = new Doctor("siva");
        Doctor john = new Doctor("john");
        Patient p1 = new Patient("p1");
        Patient p2 = new Patient("p2");
        Patient p3 = new Patient("p3");
        Patient p4 = new Patient("p4");
        Patient p5 = new Patient("p5");
        Thread doctor1 = new Thread(() -> {
            try {
                while (!room.doctorEnter(siva)) {
                }
                Thread.sleep(500);
                while (!room.doctorLeave(siva)) {
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread doctor2 = new Thread(() -> {
            try {
                while (!room.doctorEnter(john)) {
                }
                Thread.sleep(500);
                while (!room.doctorLeave(john)) {
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread patient1 = new Thread(() -> {
            try {
                while (!room.patientEnter(p1)) {
                }
                Thread.sleep(500);
                while (!room.patientLeave(p1)) {
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread patient2 = new Thread(() -> {
            try {
                while (!room.patientEnter(p2)) {
                }
                ;
                Thread.sleep(500);
                while (!room.patientLeave(p2)) {
                }
                ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread patient3 = new Thread(() -> {
            try {
                while (!room.patientEnter(p3)) {
                }
                ;
                Thread.sleep(500);
                while (!room.patientLeave(p3)) {
                }
                ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread patient4 = new Thread(() -> {
            try {
                while (!room.patientEnter(p4)) {
                }
                ;
                Thread.sleep(500);
                while (!room.patientLeave(p4)) {
                }
                ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread patient5 = new Thread(() -> {
            try {
                while (!room.patientEnter(p5)) {
                }
                ;
                Thread.sleep(500);
                while (!room.patientLeave(p5)) {
                }
                ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        doctor1.start();
        doctor2.start();
        patient1.start();
        patient2.start();
        patient3.start();
        patient4.start();
        patient5.start();
    }
}

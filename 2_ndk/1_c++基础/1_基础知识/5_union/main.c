#include <stdio.h>


//声明一个结构体
union MyStudent {
    int i;
    int j;
    double k;

};


int main() {

    union MyStudent myStudent;
    myStudent.i = 10;
    myStudent.j = 12;
    printf("i的地址 %#x% \n", &myStudent.i);
    printf("j的地址 %#x% \n", &myStudent.j);


    printf("i的值 %d\n", myStudent.i);
    printf("j的值 %d\n", myStudent.j);

    printf("共用体大小 %d \n", sizeof(myStudent));
    printf("Hello, World!\n");
    return 0;
}
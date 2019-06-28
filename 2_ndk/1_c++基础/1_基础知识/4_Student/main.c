#include <stdio.h>


//声明一个结构体
struct Student {
    int i;
    short j;
} s1, s2;


int main() {

    struct Student student;
    student.i = 10;
    student.j = 20;

    s1.i = 5;
    s2.j = 10;

    printf("Hello, World!\n");
    printf("结构体大小%d", sizeof(student));
    return 0;
}
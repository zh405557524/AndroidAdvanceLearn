#include <stdio.h>
#include "fuc.h"


//函数指针，指针以变量的形式存在
void (*funcp)(int *a, int *b);

void point_func(int *a, int *b) {
    printf("函数指针");
    *a = 200;
}


//指针函数
int *int_add_func(void *wParam) {
    printf("指针函数\n");
    int b = 10;
    int *p = &b;
    return p;
}

int main() {
    printf("Hello, World!\n");
    func();
    int a = 10;
    int_add_func(&a);
    int b = 20;
    funcp = point_func;
    funcp(&a, &b);
    printf("a值：%d", a);
    return 0;
}

int func(void) {
    printf("函数\n");
    return -1;
}


int func1(void *pathName, int a) {
    printf("函数2\n");
    return -1;
}
#include <stdio.h>


int main() {
    printf("Hello, World!\n");


    int arr[] = {100, 200, 300};
    for (int i = 0; i < 3; ++i) {
        printf("数组 %d \n", arr[i]);
    }

    //数组指针
    int *p = arr;
    *p = 400;
    *(p + 1) = 500;
    for (int i = 0; i < 3; ++i) {
        printf("修改后的数组 %d \n", arr[i]);
    }

    //指针数组
    int *i[3];
    for (int j = 0; j < 3; ++j) {

        i[j] = &arr[j];
    }
    for (int k = 0; k < 3; ++k) {
        printf("数组i:%d\n", *i[k]);
    }

    return 0;
}
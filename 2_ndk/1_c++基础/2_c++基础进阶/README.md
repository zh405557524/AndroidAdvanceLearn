# C++基础进阶

## 一、类的构造

* .h文件

~~~c++
#ifndef Student_H
#define Student_H

class Student{
  //友元函数
  friend void test(Student*)
  //友元类
  friend class Teacher;
  int i;
publick:
	Student();
	~Student();///析构函数
   setJ(int j)const;//一般方法 const 表示 不会也不允许去修改类中的成员
private:
	int j;
protected:
int k;
public:
	int l;
}

class Teacher{
 public:
  void call(Student* s){
    s->j = 10086;
  }
}

~~~

* .cpp 文件

  ~~~c++
  #include "Student.h"
  #include<iostream>
   using namespace std;
  //oncreate
  Student::Student(int i,int j):i(i){
    this->i = i;
    cout << "构造方法" <<end1;
  }
  
  //ondestroy
  Student::~Student(){
    cout <<"析构方法" <<end1;
  }
  
  void Student::setJ(int j)const{
    
  }
  
  ~~~

* cpp 文件。main文件

  ~~~c++
  #include <iostream>
  #include <Student.h>
  
  void test(Student* stu){
    stu ->j = 100;
  }
  int main(){
    Student student(10,20);
    test(&student);
    std::cout << "hell world！\n"
  }
  
  
  ~~~

  

## 二 单例对象、操作符

* 单例模式

  * .h 文件

    ~~~c++
    #pragma once
    class Student{
    private:
      static Student* instance;
      Student();
    public:
      static Stdent* getINstacne();
    }
    ~~~

    

  * .cpp文件

    ~~~c++
    #include "Student.h"
    Student* Student::instance = 0;
    
    Student* Student::getInstance(){
      if(!instance){
        instance = new Student();
      }
      return instance;
    }
    ~~~

    

  * main文件

    ~~~c++
    #include <iostream>
    #include "Student.h"
    int main(){
      Student* student = Student::getInstance();
      std::cout << "Hell World!\n"
    }
    ~~~



* 重载

  ~~~c++
  #include<iostream>
  #include "Test.h"
  void func(int a){
  
  }
  void func(float a){
  
  }
  int main(){
    Test test1;
    test1.i = 100;
    Test test2;
    test2.i = 200;
    Test test3 = test1+test2;
    std::count << test3.i <<std:end1;
  }
  
  
  ~~~

  ~~~c++
  #pragma once
  class Test{
    public:
    	int i;
  		Test operator+(const Test&t){
        Test temp;
        temp = this-> i+ t.i;
        return temp;
      };//操作符重载。+ 
  }
  ~~~

  

## 三、继承多态，虚函数

* 继承

  ~~~c++
  #pragma once
  using namespace = td;
  
  class Parent{
    public:
    virtual  void test(){//虚函数
        
      }
    virtual void test1() = 0;//子类必须实现。纯虚函数
  };
  
  class Parent1{
  public:
  	void test(){
  	 }
  }
  
  class Child: public Parent,Parent1{
    	void test(){
  	
      	}
  };
  ~~~

  ~~~c++
  #include <iostrem>
  #include "Extends.h"
  
  int main(){
  		Child child;
    	child.test();
  	
    	//静态多态
  		Parent* child = new Child();
    	child->test();
      delete child;
    	//动态函数
    	//虚函数 构造方法 永远不要设置为虚函数 析构方法 声明为虚函数
    
    	std::cout << "Hello World!\n"
  }
  ~~~

  

## 四、类模板，函数模板

~~~c++
#include <iostream>
//泛型基础 模板编程
//函数模板  java 的泛型方法
/**
* void T a(T t){}
*/
	template <typename T>
	T a(T i,T j){
    return i>j?i:j;
  }
//类模板  java的泛型类
template <class T,class E>
class Q{
  T test(T t ,E e){
    return t+e;
  }
}
int main(){
  Q<int ,float> q;
  std::cout<< q.test(1,1.f);
}

~~~



## 五 类型转换 IO 异常处理 文件读写

* 类型转换

  * C的强转

    ~~~
    C风格的强制类型转换(Type Cast)很简单，不管什么类型的转换统统是：TYPE b= (TYPE)a.
    ~~~

  * 强转类型

    * const_cast ,字面上丽姐就是去const属性

      > 修改类型的const或volatile属性 (const 类似java 的final.强转可去除final属性)

      ~~~c++
      const char *a;
      char *b = const_cast<Char*>(a);
      
      char *a ;
      const char *b = const_cast<const char*>(a);
      ~~~

    * static_cast,命名上丽姐是静态类型转换。如int 转成char

      > 1. 基础类型之间互转。如：float转成 int，int转成 unsingned int等
      >
      > 2. 指针与void之间互转。如：float*转成void* * ，bean * 转成 void * ，函数指针转成void * 等
      > 3. 子类指针/应用于父类指针/引用转换

      ~~~c++
      class Parent{
      public:
        void test(){
      		cout <<"p"<<end1;
        }
      }
      class child :public Parent{
        public:
          void test(){
            cout <<"c"<<end1;
          }
      }
      Parent *p = new Parent;
      Child *c = static_cast<Child*>(p);
      c->text()
      
      // Parent test 加上 virtual 输出p
      ~~~

      

    * dynamic_cast,命名上理解是动态类型转换。如子类和父类之间的多态类型转换。

      >主要讲基类指针、引用安全的转为派生类
  >
      >在运行期对可疑的转型操作进行安全检车，仅对多态有效

      ~~~c++
      //基类至少有一个函数
      //对指针转换失败的得到NULL，对引用失败 抛出bad_cast异常
      Parent *p = new Parent;
      Child *c = dynamic_cast<Child*>(p);
      if(!c){
        cout << "转换失败" <<end1;
      }
      
      Parent *p = new Child;
      Child *c = dynamic_cast<Child*> (p);
      if(c){
        cout <<"转换成功" << end1;
      }
      ~~~
  
  
  ​    
  
    * reinterpret_cast,仅仅重新解释类型，但没有记性二进制的转换。
    
      >对指针、引用进行原始转换
    
      ~~~c++
      float i = 10;
      // &i float指针，指向一个地址，转换为int 类型，j 就是这个地址
      int j = reinterpret_cast<int>(&i);
      cout << hex << &i << end1;
      cout << hex <<j   << end1; 
      
      cout <<hex<<i<<end1;//输出十六机制
      cout <<oct<<i<<end1;
      ~~~
    
      
    
    * char * 与 int转换
    
      ~~~c++
      //char* 转int float 
      int i= atoi("1");
      float f = atof("1.1f");
      ~~~
      
      
      
    * 注意：
    
      4种类型转换的格式，如：TYPE B = staic_cast(TYPE)a.



* 异常

  ~~~c++
  void test1(){
    throw "测试！"；
  }
  
  void test2(){
    throw exception("测试");
  }
  
  try{
    text1();
  }catch(const char *m){
    cout << m << end1;
  }
  try{
    test2();
  }catch(exception &e){
  	cout << e.what() << end1;
  }
  
  class MyException :public exception{
  public:
  	virtual char const* what() const{
      return "myexception";
    }	
  };
  //随便抛出一个对象都可以
  
  ~~~

  

* 文件与流操作

  > c 语言的文件读写操作
  >
  > 头文件:stdio.h
  >
  > 函数原型:FILE* fopen(const char* path,const char * mode);
  >
  > path:操作的文件路径
  >
  > mode:模式

~~~
模式 		 描述
 r 		  打开一个已有的文件文件，允许读取文件
 w			打开一个文件，允许写入文件。如果文件不存在，则会创建一个新文件。在这里，您的程序会从文件的					开头写入内容。如果文件存在，则该会被截断未零长度，重新写入
 a			打开一个文本文件，以追加模式写入文件。如果文件不存在，则会创建一个新文件。在这里，您的程序					会在已有的文件内容中追加内容。
 r+			打开一个文本文件，允许读写文件。
 w+ 		打开一个文本文件，允许读写文件。如果文件 已存在，则文件会被截断未零长度，如果文件不存在，则				会创建一个新文件。
 a+ 		打开一个文本文件，允许读写文件。如果文件不存在，则会创建一个新文件。读取会从文件的开头开					始，写入则只能追加模式
~~~



~~~c++
FILE *f = fopen("xxx\\t.txt","w");
fputc('a',f);
fclose(f);


FILE *f = fopen("xxx\\t.txt,","w");
char *txt = "12345";
//写入以null 结尾的字符数组
fputs(txt,f);
//格式化并输出
fprintf(f,"%s",txt);
close(f);

fgetc(f);//读取一个字符

char buff[255];
FILE *f = fopen("xxx\\t.txt","r");
//读取 遇到第一个空格字符停止
scanf(f,"%s",buff);
printf("1:%s\n",buff);

//最大读取 255-1字符
fgets(buff,255,f);
printf("2:%s\n",buff);
fclose(f);

//二进制 I/O函数
size_t fread(void *ptr,size_t size_of_elements,
            size_t number_of_elements,FILE *a_file);
size_t fwrite(const void *ptr,size_t size_of_elements,
             size_t number_of_elements,FILE *a_file);

//1、读入/读取数据缓存区
//2、每个数据项的大小
//3、多少个数据项
//4、流
//如：图片、视频等以二进制操作：
//写入buffer 有1024个字节
fwrite(buffer,1024,1,f);

~~~

>c++文件读写操作
>
><iostream> 和 <fstream>

~~~
数据类型			描述
ofstream		输出文件流、创建文件并向文件写入信息
ifstream 		输入文件流，从文件读取信息。
fstream			文件流，且同事具有ofstream和ifstream 两种功能
~~~

~~~c++
char data[100];
//以写模式打开文件
ofstream outfile;
outfile.open("XXX\\f.txt");
cout <<"输入你的名字："；
//cin 接收终端的输入
cin >> data;
//向文件写入用户输入的数据
outfile << data <<end1;
//关闭打开的文件 
outfile.close();

//以读模式打开文件
ifstream infile;
infile.open("XXX\\f.txt");
cout << "读取文件" <<end1;
infile >> data;
cout << data <<end1;
//关闭
infile.close();
~~~



## 六、容器

* 序列式

  > 序列式容器：元素排列顺序与元素本身无关，由添加顺序决定的。
  >
  > vector、list、dequeue、queue、  stack、priority、queue

  * vector

    ~~~c++
    #include <iostream>
    #include <vector>
    using namespace std;
    int main(){
      vector<int> vec_1;//实例化
      //声明有一个元素空间
      vector<int> vec_2(1);
      //6个元素值都是1
      vector<int> vec_3(6,1);
    	vector<int> vec_4(vec_3);
     
      //添加元素
      vec_1.push_back(10);
     
      //通过下标来获得元素
      cout <<"通过下标来获得元素："<< vec_1[0] <<end1;
      //直接获得首与尾的元素
      vec_1.front();//首
      vec_1.back();//尾
      vec_1.clear();//清除所有数据
      vec_1.erase(vec_1.being(),vec_1.end());//清除某一区间的元素。
      cout << "获得vec的容器大小："<< vec_1.capacity()<<end1;//获取容器大小
    }
    ~~~

    

  

* 关联式 

  >set map

  * set

    ~~~C++
    #include <iostream>
    #include <set>
    using namespace std;
    
    int main(){
    	//set集合 元素不可重复
      set<int> set1 = {1,2,3,4};
      //插入数据
      set1.insert(6);
      //判断是否插入成功
    	pair<set<Int>::interator,bool> _pair = set1.insert(5);
      //获取大小
      std:cout <<"set里面有："<< set1.size() << end1;
      //迭代器
      set<int>::iterator itt = set1.begin();
      set1.end();//指向最后一个元素的下一个元素。
      for(: itt!=set1.end();itt++){
        cout << *itt <<end1;
      }
      
    }
    ~~~

    

  * map

    ~~~C++
    #include <iostream>
    #include <map>
    int main(){
      
      map<int,string> map1;
      map<int,string> map2 = {{1,"A"},{2,"B"}};
      map2.insert({3,"C"});
      map2[3] = "D"
    }
    ~~~

    

## 七、命名空间

~~~C++
#include <iostream>
using namespace std;

//第一个命名空间
namespace first_space{
  	void func(){
  		cut <<"inside first_space" << end1;
		}
}
//第二个命名空间
namespace second_space{
  void func(){
  cut <<"outsite" << end1;
}
}

int main(){
  //调用第一个命名空间中的函数
  first_space::func();
  //调用第二个命名空间中的函数
  second_space::func();
}


~~~



## 八、引用

* c++引用vs指针

  * 不存在空引用。引用必须连接到一块合法的内存

  * 一旦引用被初始化为一个对象，就不能被指向到另一个对象。

    指针可以在任何时候指向到另一个对象。

  * 引用必须在创建时被初始化。指针可以在任何时间被初始化。

    



~~~C++
#include <iostream>

int main(){
  //引用类型
  int i = 17;
  double d = 19.0;
  int* p = &i;
  int& r = i;
  double& s = d; //类型后面加 &  则代表是引用类型
  cout << "Value of refrence:" << r <<end1;
}
~~~


















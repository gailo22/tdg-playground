#include <iostream>
#include "functions.h"
#include "Account.h"

using namespace std;

int main()
{
    Account a1;
    a1.Deposit(100);

    cout << "Hello World " << add(1, 2) << ", " << a1.GetBalance() << endl;
}
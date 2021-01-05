#include "Account.h"

using std::vector;
using std::string;
using std::to_string;

Account::Account() : balance(0)
{

}

vector<string> Account::Report()
{
    vector<string> report;

    return report;
}

bool Account::Deposit(int amount) {
    balance += amount;
    return false;
}

bool Account::Withdraw(int amount) {
    return false;
}

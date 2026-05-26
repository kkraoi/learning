public class Bank {
  String accountNumber;
  int balance;

  public String getAccountNumber() {
    return this.accountNumber;
  }
  public int getBalance() {
    return this.balance;
  }

  public String toString() {
    return "¥¥" + this.getBalance() + "(口座番号：" + this.getAccountNumber() + ")";
  }

  public boolean equals(Object o) {
    if(this == o) {return true;}
    if( o instanceof Bank a) {
      String an1 = this.getAccountNumber().trim();
      String an2 = a.getAccountNumber().trim();
      if(an1.equals(an2)) {return true;}
    }
    return false;
  }

  public String showAccount(String a) {
    int accountNumber = a.trim();
    if(accountNumber.equals(this.getAccountNumber())) {
      return "¥¥" + this.getBalance() + "(口座番号：" + this.getAccountNumber() + ")";
    }
  }
}
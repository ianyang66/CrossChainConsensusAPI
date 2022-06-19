// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.4.26;
contract InfoContract {

   string info;

   function setInfo(string _info) public {
       info = _info;
   }

   function getInfo() public constant returns (string) {
       return (info);
   }   

}
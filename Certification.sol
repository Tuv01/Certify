pragma solidity >=0.4.24;

contract Owned{

   
    /* Define variable owner of the type address */
    address payable owner;

    /* This function is executed at initialization and sets the owner of the contract */
    function owned () public {
        owner = msg.sender;
    }

    modifier onlyOwner {
        require (msg.sender == owner);
        _;
    }

    /* Function to recover the funds on the contract */
    function kill() public {
      if(msg.sender == owner) selfdestruct(owner);
    }
}

contract Certification is Owned {

    struct Degree {
        string degreeInformation;
    }

    mapping(bytes32 => Degree) degrees;
    bytes32[] public degreesConf;

    function setDegree(bytes32 _sha256,string memory _degreeInformation) onlyOwner public {
        Degree storage degree = degrees[_sha256];
        degree.degreeInformation = _degreeInformation;
        degreesConf.push(_sha256) -1;
    }

    function getDegrees() view public returns(bytes32[] memory){
        return degreesConf;
    }

    function getDegrees(bytes32 _sha256) view public returns (string memory){
        return (degrees[_sha256].degreeInformation);
    }

    function countDegrees() view public returns (uint){
       return degreesConf.length;
    }

   function deleteDegree(bytes32 _sha256) onlyOwner public {
       delete degrees[_sha256];
   }

}
package com.tuv01.certification;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.5.
 */
@SuppressWarnings("rawtypes")
public class Certification extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5061059c806100206000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c8063ad5d4f991161005b578063ad5d4f9914610190578063c47e93e614610198578063df32754b1461022a578063f6d840911461023257610088565b80632955c49f1461008d57806341c0e1b51461013c578063908216111461014457806391f2bceb14610173575b600080fd5b61013a600480360360408110156100a357600080fd5b813591908101906040810160208201356401000000008111156100c557600080fd5b8201836020820111156100d757600080fd5b803590602001918460018302840111640100000000831117156100f957600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061028a945050505050565b005b61013a6102ff565b6101616004803603602081101561015a57600080fd5b5035610322565b60408051918252519081900360200190f35b61013a6004803603602081101561018957600080fd5b5035610340565b610161610375565b6101b5600480360360208110156101ae57600080fd5b503561037c565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101ef5781810151838201526020016101d7565b50505050905090810190601f16801561021c5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61013a61041c565b61023a610430565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561027657818101518382015260200161025e565b505050509050019250505060405180910390f35b6000546001600160a01b031633146102a157600080fd5b6000828152600160209081526040909120825190916102c4918391850190610488565b5050600280546001810182556000919091527f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace019190915550565b6000546001600160a01b0316331415610320576000546001600160a01b0316ff5b565b6002818154811061032f57fe5b600091825260209091200154905081565b6000546001600160a01b0316331461035757600080fd5b6000818152600160205260408120906103708282610506565b505050565b6002545b90565b60008181526001602081815260409283902080548451600294821615610100026000190190911693909304601f810183900483028401830190945283835260609390918301828280156104105780601f106103e557610100808354040283529160200191610410565b820191906000526020600020905b8154815290600101906020018083116103f357829003601f168201915b50505050509050919050565b600080546001600160a01b03191633179055565b6060600280548060200260200160405190810160405280929190818152602001828054801561047e57602002820191906000526020600020905b81548152602001906001019080831161046a575b5050505050905090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106104c957805160ff19168380011785556104f6565b828001600101855582156104f6579182015b828111156104f65782518255916020019190600101906104db565b5061050292915061054d565b5090565b50805460018160011615610100020316600290046000825580601f1061052c575061054a565b601f01602090049060005260206000209081019061054a919061054d565b50565b61037991905b80821115610502576000815560010161055356fea265627a7a72315820021bdf9bed736eb4a4872042ccdd91063b752354c2541627a9231c47d33cd27164736f6c634300050b0032";

    public static final String FUNC_SETDEGREE = "setDegree";

    public static final String FUNC_KILL = "kill";

    public static final String FUNC_DEGREESCONF = "degreesConf";

    public static final String FUNC_DELETEDEGREE = "deleteDegree";

    public static final String FUNC_COUNTDEGREES = "countDegrees";

    public static final String FUNC_getDegrees = "getDegrees";

    public static final String FUNC_OWNED = "owned";

    @Deprecated
    protected Certification(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Certification(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Certification(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Certification(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> setDegree(byte[] _sha256, String _degreeInformation) {
        final Function function = new Function(
                FUNC_SETDEGREE, 
                Arrays.<Type>asList(new Bytes32(_sha256),
                new Utf8String(_degreeInformation)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> kill() {
        final Function function = new Function(
                FUNC_KILL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> degreesConf(BigInteger param0) {
        final Function function = new Function(FUNC_DEGREESCONF, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> deleteDegree(byte[] _sha256) {
        final Function function = new Function(
                FUNC_DELETEDEGREE, 
                Arrays.<Type>asList(new Bytes32(_sha256)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> countDegrees() {
        final Function function = new Function(FUNC_COUNTDEGREES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getDegrees(byte[] _sha256) {
        final Function function = new Function(FUNC_getDegrees, 
                Arrays.<Type>asList(new Bytes32(_sha256)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> owned() {
        final Function function = new Function(
                FUNC_OWNED, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getDegrees() {
        final Function function = new Function(FUNC_getDegrees, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    @Deprecated
    public static Certification load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Certification(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Certification load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Certification(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Certification load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Certification(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Certification load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Certification(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Certification> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Certification.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Certification> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Certification.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Certification> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Certification.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Certification> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Certification.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}

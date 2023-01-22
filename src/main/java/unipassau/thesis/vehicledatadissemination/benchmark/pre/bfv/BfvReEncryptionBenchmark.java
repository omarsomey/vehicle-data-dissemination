package unipassau.thesis.vehicledatadissemination.benchmark.pre.bfv;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import unipassau.thesis.vehicledatadissemination.model.ProxyReEncryptionModel;
import unipassau.thesis.vehicledatadissemination.util.OpenPRE;

import java.util.concurrent.TimeUnit;

public class BfvReEncryptionBenchmark {
    public static String dataDumpFolder = System.getProperty("user.dir") + "/data_dump/BFV/";
    public static String cryptoContextFileName = "benchmark-crypto-context";
    public static String cryptoContextpath = dataDumpFolder + cryptoContextFileName;

    public static String privateKeyAlice = dataDumpFolder + "benchmark-alice-private-key";
    public static String publicKeyAlice =  dataDumpFolder + "benchmark-alice-public-key";
    public static String publicKeyBob =  dataDumpFolder + "benchmark-bob-public-key";
    public static String privateKeyBob = dataDumpFolder + "benchmark-bob-private-key";
    public static String reKey = dataDumpFolder + "benchmark-a2b";
    public static String ciphertextPath = dataDumpFolder + "ciphertext";
    public static String ciphertextReEncrypted = dataDumpFolder + "ciphertext-re-enc";




    static final int iter = 5;
    static final int fork = 1;
    static final int warmIter = 3;
    static final int iter_time = 8;


    @State(Scope.Thread)
    public static class BFVBenchmarkStateReEncrypt {
        ProxyReEncryptionModel pre =new ProxyReEncryptionModel("BFV", 256, 8192, ProxyReEncryptionModel.SecurityLevel.SECURITY_LEVEL_128);
        String randomPlaintext;


        @Setup(Level.Invocation)
        public void setup() {
            OpenPRE.INSTANCE.cryptoContextGen(pre.getSchemeName(),
                    dataDumpFolder,
                    cryptoContextFileName,
                    256,
                    pre.getRingSize(),
                    pre.getSecurityLevel().toString());
            OpenPRE.INSTANCE.keysGen(cryptoContextpath, dataDumpFolder + "benchmark-alice");
            OpenPRE.INSTANCE.keysGen(cryptoContextpath, dataDumpFolder + "benchmark-bob");
            OpenPRE.INSTANCE.reKeyGen(privateKeyAlice, publicKeyBob, reKey);
            randomPlaintext= RandomStringUtils.randomAlphanumeric(pre.getRingSize());
            OpenPRE.INSTANCE.encrypt(publicKeyAlice, randomPlaintext, ciphertextPath);

        }

    }

    @Benchmark
    @Warmup(iterations = warmIter, time = iter_time, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = iter, time = iter_time, timeUnit = TimeUnit.SECONDS)
    @Fork(value = fork)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void reEncrypt_CC_BFV_128_8192(BFVBenchmarkStateReEncrypt mystate) {
        mystate.pre.setRingSize(8192);
        mystate.pre.setSecurityLevel(ProxyReEncryptionModel.SecurityLevel.SECURITY_LEVEL_128);
        OpenPRE.INSTANCE.reEncrypt(ciphertextPath, reKey + "-re-enc-key", ciphertextReEncrypted);
    }

    @Benchmark
    @Warmup(iterations = warmIter, time = iter_time, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = iter, time = iter_time, timeUnit = TimeUnit.SECONDS)
    @Fork(value = fork)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void reEncrypt_CC_BFV_128_16384(BFVBenchmarkStateReEncrypt mystate) {
        mystate.pre.setRingSize(16384);
        mystate.pre.setSecurityLevel(ProxyReEncryptionModel.SecurityLevel.SECURITY_LEVEL_128);
        OpenPRE.INSTANCE.reEncrypt(ciphertextPath, reKey + "-re-enc-key", ciphertextReEncrypted);
    }

    @Benchmark
    @Warmup(iterations = warmIter, time = iter_time, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = iter, time = iter_time, timeUnit = TimeUnit.SECONDS)
    @Fork(value = fork)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void reEncrypt_CC_BFV_128_32768(BFVBenchmarkStateReEncrypt mystate) {
        mystate.pre.setRingSize(32768);
        mystate.pre.setSecurityLevel(ProxyReEncryptionModel.SecurityLevel.SECURITY_LEVEL_128);
        OpenPRE.INSTANCE.reEncrypt(ciphertextPath, reKey + "-re-enc-key", ciphertextReEncrypted);    }



    @Benchmark
    @Warmup(iterations = warmIter, time = iter_time, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = iter, time = iter_time, timeUnit = TimeUnit.SECONDS)
    @Fork(value = fork)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void reEncrypt_CC_BFV_128_4096(BFVBenchmarkStateReEncrypt mystate) {
        mystate.pre.setRingSize(4096);
        mystate.pre.setSecurityLevel(ProxyReEncryptionModel.SecurityLevel.SECURITY_LEVEL_128);
        OpenPRE.INSTANCE.reEncrypt(ciphertextPath, reKey + "-re-enc-key", ciphertextReEncrypted);    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(BfvReEncryptionBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }
}

/*
 * java-tron is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * java-tron is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.tron.core;

import static org.tron.core.Blockchain.GENESIS_COINBASE_DATA;

import com.google.protobuf.ByteString;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tron.common.storage.leveldb.LevelDbDataSourceImpl;
import org.tron.common.utils.ByteArray;
import org.tron.core.capsule.utils.BlockUtil;
import org.tron.core.capsule.utils.TransactionUtil;
import org.tron.protos.Protocal.Block;
import org.tron.protos.Protocal.Transaction;


public class BlockCapsuleTest {

  private static final Logger logger = LoggerFactory.getLogger("Test");

  @Test
  public void testNewBlock() {
    ByteString parentHash = ByteString.copyFrom(ByteArray.fromHexString(
        "0304f784e4e7bae517bcab94c3e0c9214fb4ac7ff9d7d5a937d1f40031f87b85"));
    ByteString difficulty = ByteString.copyFrom(ByteArray.fromHexString("2001"));
    Block block = BlockUtil.newBlock(null, parentHash, difficulty, 0);

    logger.info("test new block: {}", BlockUtil.toPrintString(block));
  }

  @Test
  public void testNewGenesisBlock() {
    Transaction coinbase = TransactionUtil.newCoinbaseTransaction(
        "0304f784e4e7bae517bcab94c3e0c9214fb4ac7ff9d7d5a937d1f40031f87b85", GENESIS_COINBASE_DATA,
        0);
    Block genesisBlock = BlockUtil.newGenesisBlock(coinbase);

    logger.info("test new genesis block: {}", BlockUtil.toPrintString(genesisBlock));
  }

  @Test
  public void testPrepareData() {
    Transaction coinbase = TransactionUtil.newCoinbaseTransaction(
        "0304f784e4e7bae517bcab94c3e0c9214fb4ac7ff9d7d5a937d1f40031f87b85", GENESIS_COINBASE_DATA,
        0);
    logger.info("test prepare data: {}",
        "12580a2015f3988aa8d56eab3bfca45144bad77fc60acce50437a0a9d794a03a83c15c5"
            + "e120e10ffffffffffffffffff012201001a24080a12200304f784e4e7bae517bcab94c"
            + "3e0c9214fb4ac7ff9d7d5a937d1f40031f87b8532022001"
            .equals(ByteArray
                .toHexString(
                    BlockUtil.prepareData(BlockUtil.newGenesisBlock(coinbase)))));
  }

  @Test
  public void testIsValidate() {
    Transaction coinbase = TransactionUtil
        .newCoinbaseTransaction("0304f784e4e7bae517bcab94c3e0c9214fb4ac7ff9d7d5a937d1f40031f87b85",
            GENESIS_COINBASE_DATA,
            0);
    Block genesisBlock = BlockUtil.newGenesisBlock(coinbase);
    logger.info("nonce: {}",
        ByteArray.toHexString(genesisBlock.getBlockHeader().getNonce().toByteArray()));
  }

  @Test
  public void testToPrintString() {
    Transaction coinbase = TransactionUtil
        .newCoinbaseTransaction("0304f784e4e7bae517bcab94c3e0c9214fb4ac7ff9d7d5a937d1f40031f87b85",
            GENESIS_COINBASE_DATA,
            0);
    logger.info("test to print string: {}",
        BlockUtil.toPrintString(BlockUtil.newGenesisBlock(coinbase)));
  }

  @Test
  public void testGetMineValue() {
    Transaction coinbase = TransactionUtil
        .newCoinbaseTransaction("0304f784e4e7bae517bcab94c3e0c9214fb4ac7ff9d7d5a937d1f40031f87b85",
            GENESIS_COINBASE_DATA,
            0);
    logger.info("test get mine value: {}",
        ByteArray.toHexString(BlockUtil.getMineValue(BlockUtil.newGenesisBlock(coinbase)
        )));
  }

  @Test
  public void testGetPowBoundary() {
    Transaction coinbase = TransactionUtil
        .newCoinbaseTransaction("0304f784e4e7bae517bcab94c3e0c9214fb4ac7ff9d7d5a937d1f40031f87b85",
            GENESIS_COINBASE_DATA,
            0);
    logger.info("test get pow boundary: {}",
        ByteArray.toHexString(BlockUtil.getPowBoundary(BlockUtil.newGenesisBlock(coinbase))));
  }

  @Test
  public void testGetIncreaseNumber() {
    LevelDbDataSourceImpl mockDb = Mockito.mock(LevelDbDataSourceImpl.class);

    Blockchain mockBlockchain = Mockito.mock(Blockchain.class);
    Mockito.when(mockBlockchain.getBlockDB()).thenReturn(mockDb);

    logger.info("test getData increase number: {}", BlockUtil
        .getIncreaseNumber(mockBlockchain));
  }
}

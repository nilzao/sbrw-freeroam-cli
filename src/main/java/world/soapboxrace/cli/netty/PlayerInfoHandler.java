package world.soapboxrace.cli.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import world.soapboxrace.cli.Car;
import world.soapboxrace.cli.CarProtocol;
import world.soapboxrace.cli.MainBoard;
import world.soapboxrace.cli.Sender;
import world.soapboxrace.cli.SenderState;

public class PlayerInfoHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		DatagramPacket datagramPacket = (DatagramPacket) msg;
		ByteBuf buf = datagramPacket.content();
		if (isPlayerInfoPacket(buf)) {
			Sender.setSenderState(SenderState.INFO);
			parsePlayers(buf);
		}
		super.channelRead(ctx, msg);
	}

	private int parse(ByteBuf buffer) {
		buffer.readBytes(2);
		return buffer.indexOf(buffer.readerIndex(), 999, (byte) 0xff);
	}

	private void process(ByteBuf buffer) {
		buffer.setIndex(2, 999);
		int indexOf = 0;
		while ((indexOf = parse(buffer)) != -1) {
			ByteBuf readBytes = buffer.readBytes(indexOf - buffer.readerIndex());
			byte[] bytes = ByteBufUtil.getBytes(readBytes);
			if (bytes.length > 0) {
				CarProtocol carProtocol = new CarProtocol();
				carProtocol.deserialize(bytes);
				Car car = new Car(carProtocol.getPlayerId(), carProtocol.getX(), carProtocol.getY());
				MainBoard.addUpdateCar(car);
			}
		}
		buffer.setIndex(0, 0);
	}

	private void parsePlayers(ByteBuf buf) {
		process(buf);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.err.println(cause.getMessage());
		ctx.close();
	}

	private boolean isPlayerInfoPacket(ByteBuf buf) {
		return (buf.getByte(2) == (byte) 0x07);
	}

}

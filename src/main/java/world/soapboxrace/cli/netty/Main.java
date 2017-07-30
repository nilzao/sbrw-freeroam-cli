package world.soapboxrace.cli.netty;

import java.awt.EventQueue;
import java.net.InetSocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import world.soapboxrace.cli.MainBoard;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainBoard();
			}
		});

		String host = "localhost";
		int port = 9999;

		if (args.length == 2) {
			host = args[0];
			port = Integer.parseInt(args[1]);
		}

		InetSocketAddress remoteAddress = new InetSocketAddress(host, port);

		NettyUdpClient client = new NettyUdpClient();

		try {
			ChannelFuture channelFuture = client.start();

			String text = "test";
			System.out.println("Client sending message " + text + " to server");
			ByteBuf byteBuf = Unpooled.copiedBuffer(text, CharsetUtil.UTF_8);
			client.write(new DatagramPacket(byteBuf, remoteAddress));

			// Wait until the connection is closed.
			channelFuture.channel().closeFuture().sync();

		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}

	}
}

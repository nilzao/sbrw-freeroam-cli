package world.soapboxrace.cli.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.DatagramChannel;

public class ClientChannelInitializer extends ChannelInitializer<DatagramChannel> {
	@Override
	protected void initChannel(DatagramChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("echo", new ExampleHandler());
	}
}

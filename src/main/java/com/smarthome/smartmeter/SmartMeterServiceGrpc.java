package com.smarthome.smartmeter;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: smartmeter.proto")
public final class SmartMeterServiceGrpc {

  private SmartMeterServiceGrpc() {}

  public static final String SERVICE_NAME = "smartmeter.SmartMeterService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.smarthome.smartmeter.GenerateEnergyReportRequest,
      com.smarthome.smartmeter.ReportEntry> getGenerateEnergyReportMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GenerateEnergyReport",
      requestType = com.smarthome.smartmeter.GenerateEnergyReportRequest.class,
      responseType = com.smarthome.smartmeter.ReportEntry.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.smarthome.smartmeter.GenerateEnergyReportRequest,
      com.smarthome.smartmeter.ReportEntry> getGenerateEnergyReportMethod() {
    io.grpc.MethodDescriptor<com.smarthome.smartmeter.GenerateEnergyReportRequest, com.smarthome.smartmeter.ReportEntry> getGenerateEnergyReportMethod;
    if ((getGenerateEnergyReportMethod = SmartMeterServiceGrpc.getGenerateEnergyReportMethod) == null) {
      synchronized (SmartMeterServiceGrpc.class) {
        if ((getGenerateEnergyReportMethod = SmartMeterServiceGrpc.getGenerateEnergyReportMethod) == null) {
          SmartMeterServiceGrpc.getGenerateEnergyReportMethod = getGenerateEnergyReportMethod = 
              io.grpc.MethodDescriptor.<com.smarthome.smartmeter.GenerateEnergyReportRequest, com.smarthome.smartmeter.ReportEntry>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "smartmeter.SmartMeterService", "GenerateEnergyReport"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.smarthome.smartmeter.GenerateEnergyReportRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.smarthome.smartmeter.ReportEntry.getDefaultInstance()))
                  .setSchemaDescriptor(new SmartMeterServiceMethodDescriptorSupplier("GenerateEnergyReport"))
                  .build();
          }
        }
     }
     return getGenerateEnergyReportMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.smarthome.smartmeter.ConsumptionReading,
      com.smarthome.smartmeter.ConsumptionSummary> getUploadConsumptionReadingsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UploadConsumptionReadings",
      requestType = com.smarthome.smartmeter.ConsumptionReading.class,
      responseType = com.smarthome.smartmeter.ConsumptionSummary.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.smarthome.smartmeter.ConsumptionReading,
      com.smarthome.smartmeter.ConsumptionSummary> getUploadConsumptionReadingsMethod() {
    io.grpc.MethodDescriptor<com.smarthome.smartmeter.ConsumptionReading, com.smarthome.smartmeter.ConsumptionSummary> getUploadConsumptionReadingsMethod;
    if ((getUploadConsumptionReadingsMethod = SmartMeterServiceGrpc.getUploadConsumptionReadingsMethod) == null) {
      synchronized (SmartMeterServiceGrpc.class) {
        if ((getUploadConsumptionReadingsMethod = SmartMeterServiceGrpc.getUploadConsumptionReadingsMethod) == null) {
          SmartMeterServiceGrpc.getUploadConsumptionReadingsMethod = getUploadConsumptionReadingsMethod = 
              io.grpc.MethodDescriptor.<com.smarthome.smartmeter.ConsumptionReading, com.smarthome.smartmeter.ConsumptionSummary>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "smartmeter.SmartMeterService", "UploadConsumptionReadings"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.smarthome.smartmeter.ConsumptionReading.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.smarthome.smartmeter.ConsumptionSummary.getDefaultInstance()))
                  .setSchemaDescriptor(new SmartMeterServiceMethodDescriptorSupplier("UploadConsumptionReadings"))
                  .build();
          }
        }
     }
     return getUploadConsumptionReadingsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SmartMeterServiceStub newStub(io.grpc.Channel channel) {
    return new SmartMeterServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SmartMeterServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SmartMeterServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SmartMeterServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SmartMeterServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SmartMeterServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void generateEnergyReport(com.smarthome.smartmeter.GenerateEnergyReportRequest request,
        io.grpc.stub.StreamObserver<com.smarthome.smartmeter.ReportEntry> responseObserver) {
      asyncUnimplementedUnaryCall(getGenerateEnergyReportMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.smarthome.smartmeter.ConsumptionReading> uploadConsumptionReadings(
        io.grpc.stub.StreamObserver<com.smarthome.smartmeter.ConsumptionSummary> responseObserver) {
      return asyncUnimplementedStreamingCall(getUploadConsumptionReadingsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGenerateEnergyReportMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                com.smarthome.smartmeter.GenerateEnergyReportRequest,
                com.smarthome.smartmeter.ReportEntry>(
                  this, METHODID_GENERATE_ENERGY_REPORT)))
          .addMethod(
            getUploadConsumptionReadingsMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                com.smarthome.smartmeter.ConsumptionReading,
                com.smarthome.smartmeter.ConsumptionSummary>(
                  this, METHODID_UPLOAD_CONSUMPTION_READINGS)))
          .build();
    }
  }

  /**
   */
  public static final class SmartMeterServiceStub extends io.grpc.stub.AbstractStub<SmartMeterServiceStub> {
    private SmartMeterServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SmartMeterServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartMeterServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SmartMeterServiceStub(channel, callOptions);
    }

    /**
     */
    public void generateEnergyReport(com.smarthome.smartmeter.GenerateEnergyReportRequest request,
        io.grpc.stub.StreamObserver<com.smarthome.smartmeter.ReportEntry> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGenerateEnergyReportMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.smarthome.smartmeter.ConsumptionReading> uploadConsumptionReadings(
        io.grpc.stub.StreamObserver<com.smarthome.smartmeter.ConsumptionSummary> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getUploadConsumptionReadingsMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class SmartMeterServiceBlockingStub extends io.grpc.stub.AbstractStub<SmartMeterServiceBlockingStub> {
    private SmartMeterServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SmartMeterServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartMeterServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SmartMeterServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<com.smarthome.smartmeter.ReportEntry> generateEnergyReport(
        com.smarthome.smartmeter.GenerateEnergyReportRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getGenerateEnergyReportMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SmartMeterServiceFutureStub extends io.grpc.stub.AbstractStub<SmartMeterServiceFutureStub> {
    private SmartMeterServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SmartMeterServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartMeterServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SmartMeterServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_GENERATE_ENERGY_REPORT = 0;
  private static final int METHODID_UPLOAD_CONSUMPTION_READINGS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SmartMeterServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SmartMeterServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GENERATE_ENERGY_REPORT:
          serviceImpl.generateEnergyReport((com.smarthome.smartmeter.GenerateEnergyReportRequest) request,
              (io.grpc.stub.StreamObserver<com.smarthome.smartmeter.ReportEntry>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPLOAD_CONSUMPTION_READINGS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.uploadConsumptionReadings(
              (io.grpc.stub.StreamObserver<com.smarthome.smartmeter.ConsumptionSummary>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SmartMeterServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SmartMeterServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.smarthome.smartmeter.SmartMeterProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SmartMeterService");
    }
  }

  private static final class SmartMeterServiceFileDescriptorSupplier
      extends SmartMeterServiceBaseDescriptorSupplier {
    SmartMeterServiceFileDescriptorSupplier() {}
  }

  private static final class SmartMeterServiceMethodDescriptorSupplier
      extends SmartMeterServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SmartMeterServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SmartMeterServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SmartMeterServiceFileDescriptorSupplier())
              .addMethod(getGenerateEnergyReportMethod())
              .addMethod(getUploadConsumptionReadingsMethod())
              .build();
        }
      }
    }
    return result;
  }
}

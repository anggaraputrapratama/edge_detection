import 'dart:async';

import 'package:flutter/services.dart';

class EdgeDetection {
  static const MethodChannel _channel = const MethodChannel('edge_detection');

  /// Call this method to scan the object edge in live camera.
  static Future<bool> detectEdge(
    String saveCropTo,
    String savePictureTo, {
    bool canUseGallery = true,
    String androidScanTitle = "Scanning",
    String androidCropTitle = "Crop",
    // String androidCropBlackWhiteTitle = "Black White",
    // String androidCropReset: "Reset",
  }) async {
    return await _channel.invokeMethod('edge_detect', {
      'save_crop_to': saveCropTo,
      'can_use_gallery': canUseGallery,
      'scan_title': androidScanTitle,
      'save_picture_to': savePictureTo
      // 'crop_title': androidCropTitle,
      // 'crop_black_white_title': androidCropBlackWhiteTitle,
      // 'crop_reset_title': androidCropReset,
    });
  }

  /// Call this method to scan the object edge from a gallery image.
  static Future<bool> detectEdgeFromGallery(
    String saveCropTo, {
    String androidCropTitle = "Crop",
    String androidCropBlackWhiteTitle = "Black White",
    String androidCropReset = "Reset",
  }) async {
    print('aqui1:$saveCropTo');
    return await _channel.invokeMethod('edge_detect_gallery', {
      'save_crop_to': saveCropTo,
      // 'crop_title': androidCropTitle,
      // 'crop_black_white_title': androidCropBlackWhiteTitle,
      // 'crop_reset_title': androidCropReset,
      'from_gallery': true,
    });
  }
}

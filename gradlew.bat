import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:vegood/controllers/size_config.dart';


List<String> coverPhotos =[
  'https://images.pexels.com/photos/6267/menu-restaurant-vintage-table.jpg',
  'https://images.pexels.com/photos/1267320/pexels-photo-1267320.jpeg',
  'https://images.pexels.com/photos/260922/pexels-photo-260922.jpeg',
];

class CoverPhotoList extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: 30 * SizeConfig.blockSizeVertical,
      child: Container(
        decoration: BoxDecoration(
          color: Color(0xFF878787),
        ),
        child: ListView.builder(
          scrollDirection: Axis.horizontal,
          itemCount: 3,
          itemBuilder: (_, index) => Container(
            width: SizeConfig.screenWidth,
            decoration: BoxDecoration(
              image: DecorationImage(
                image: CachedNetworkImageProvider(coverPhotos[index],),
                fit: BoxFit.cover,
              ),
            ),
          ),
          ),
      ),
    );
  }
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
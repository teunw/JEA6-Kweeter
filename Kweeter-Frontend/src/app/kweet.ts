import {IProfile} from './profile';

export interface IKweet {
  publicId: string;
  textContent: string;
  date: Date;
  author: IProfile;
  responses: IKweet[];
  likedBy: IProfile[];
  rekweets: IKweet[];
  internalId: number;
}

export interface IKweetPost {
  profileId: number;
  textContent: string;
}

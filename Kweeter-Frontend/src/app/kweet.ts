import {Profile} from './profile';

export class Kweet {
  public publicId: string;
  public textContent: string;
  public date: Date;
  public author: Profile;
  public responses: Kweet[];
  public likedBy: Profile[];
  public rekweets: Kweet[];
  public internalId: number;
}

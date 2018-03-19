import {IProfile} from './profile';

export interface IProfileFollower {
  id: number;
  profile: IProfile;
  followingProfiles: IProfile[];
}

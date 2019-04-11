import React, {Component} from 'react'
import {css} from 'emotion';
import {Route, Switch, Redirect} from 'react-router-dom';
import UserPreviews from './UserPreviews'
import GroupPreviews from "./GroupPreviews";
import UserActions from '../Actions/UserActions';
import GroupActions from '../Actions/GroupActions';
import AuthStore from '../Store/AuthStore';
import UserStore from '../Store/UserStore';
import GroupStore from '../Store/GroupStore';
import GroupOrUserBar from './GroupOrUserBar'
import SearchUsers from "./SearchUsers";
import SearchGroups from "./SearchGroups";


class UserSearch extends Component{
    constructor(props){
        super(props);
        this.state = {
            users: [],
            groups: [],
            userButtonSelected: true
        };
        this._onUsersChanged = this._onUsersChanged.bind(this);
        this._onGroupsChanged = this._onGroupsChanged.bind(this);
        this.userButtonPressed = this.userButtonPressed.bind(this);
        this.groupButtonPressed = this.groupButtonPressed.bind(this);
    }

    componentWillMount(){
        UserStore.addUserListChangeListener(this._onUsersChanged);
        GroupStore.addGroupsChageListner(this._onGroupsChanged);
        if(this.state.userButtonSelected === true){
            UserActions.getUsers(AuthStore._getAuthUser().username);
        }else{
            GroupActions.getAllGroups(AuthStore._getAuthUser().username);
        }
    }


    componentDidMount(){
        this.setState({
            users: UserStore._getUsers(),
            groups: GroupStore._getAllGroups(),
        })
    }

    componentWillUnmount(){
        UserStore.removeUserListChangeListener(this._onUsersChanged);
        GroupStore.removeGroupsListener(this._onGroupsChanged);
    }

    _onUsersChanged(){
        const users = UserStore._getUsers();

        console.log(users);
        this.setState({
            users: UserStore._getUsers(),
        })
    }

    _onGroupsChanged(){
        const groups = GroupStore._getAllGroups();
        console.log(groups);
        this.setState({
            groups: groups,
        })
    }

    groupButtonPressed(){
        // if(this.state.userButtonSelected) {
        //     GroupActions.getAllGroups(AuthStore._getAuthUser().username);
        // }
        this.setState({
            userButtonSelected: false,
        });
    }

    userButtonPressed(){
        // if(!this.state.userButtonSelected) {
        //     UserActions.getUsers(AuthStore._getAuthUser().username);
        // }
        this.setState({
            userButtonSelected: true,
        });
    }

    render() {
        return(
            <div className={css({
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'space-between',
                height: '100%',
                overflowX: 'hidden',
            })}>
                <div className={css({paddingBottom: '3em'})}>
                    <GroupOrUserBar userButtonSelected={this.state.userButtonSelected}
                                    onUserClick={this.userButtonPressed}
                                    onGroupClick={this.groupButtonPressed}/>
                </div>
                <Switch>
                    <Route path="/search-users">
                        {() => <SearchUsers/>}
                    </Route>
                    <Route path="/search-groups">
                        {() => <SearchGroups/>}
                    </Route>
                </Switch>
                {/*{this.state.userButtonSelected ?*/}
                    {/*this.state.users.map(user => {*/}
                        {/*return( <UserPreviews user={user}*/}
                                              {/*profileOnClick={props.profileOnClick}/>)*/}
                    {/*}) :*/}
                    {/*this.state.groups.map(group => {*/}
                        {/*return( <GroupPreviews group={group}/>)*/}
                    {/*})*/}
                {/*}*/}
            </div>
        )
    }
}

export default UserSearch;